package br.edu.ifsul.tsi.vagas.api.vagas;

import br.edu.ifsul.tsi.vagas.api.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VagaService {
    @Autowired
    private VagaRepository rep;

    public List<VagaDTO> getVagas() {
        return rep.findAll().stream().map(VagaDTO::create).collect(Collectors.toList());
    }

    public VagaDTO getVagaById(Long id) {
        Optional<Vaga> vaga = rep.findById(id);
        return vaga.map(VagaDTO::create).orElseThrow(() -> new ObjectNotFoundException("Vaga não encontrada"));
    }

    public List<VagaDTO> getVagasByNome(String nome) {
        return rep.findByNome(nome).stream().map(VagaDTO::create).collect(Collectors.toList());
    }

    public VagaDTO insert(Vaga vaga) {
        Assert.isNull(vaga.getId(),"Não foi possível inserir o registro");

        return VagaDTO.create(rep.save(vaga));
    }

    public VagaDTO update(Vaga vaga, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca a vaga no banco de dados
        Optional<Vaga> optional = rep.findById(id);
        if(optional.isPresent()) {
            Vaga db = optional.get();
            // Copiar as propriedades
            db.setNome(vaga.getNome());
            db.setAtribuicoes(vaga.getAtribuicoes());
            db.setRequisitos(vaga.getRequisitos());
            db.setHorario(vaga.getHorario());
            db.setBeneficios(vaga.getBeneficios());
            System.out.println("Vaga id " + db.getId());

            // Atualiza a Vaga
            rep.save(db);

            return VagaDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
