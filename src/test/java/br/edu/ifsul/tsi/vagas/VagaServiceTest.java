package br.edu.ifsul.tsi.vagas;

import br.edu.ifsul.tsi.vagas.api.infra.exception.ObjectNotFoundException;
import br.edu.ifsul.tsi.vagas.api.vagas.Vaga;
import br.edu.ifsul.tsi.vagas.api.vagas.VagaDTO;
import br.edu.ifsul.tsi.vagas.api.vagas.VagaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VagaServiceTest {

    @Autowired
    private VagaService service;

    @Test
    public void testGetVagas() {
        List<VagaDTO> vagas = service.getVagas();
        assertEquals(5, vagas.size());
    }

    @Test
    public void testGetVagaById(){
        VagaDTO v = service.getVagaById(4L);
        assertNotNull(v);
        assertEquals("Operador de Caixa", v.getNome());
    }

    @Test
    public void getVagasByNome(){
        assertEquals(1, service.getVagasByNome("Analista de Redes").size());
        assertEquals(1, service.getVagasByNome("Padeiro").size());
        assertEquals(1, service.getVagasByNome("Técnico de Redes").size());
    }

    @Test
    public void testInsert() {

        //cria o produto para teste
        Vaga vaga = new Vaga();
        vaga.setNome("Teste");
        vaga.setBeneficios("Ben. do produto Teste");
        vaga.setAtribuicoes("Atrib Teste");
        vaga.setHorario("Horario de Teste");
        vaga.setRequisitos("Req de Teste");

        //insere o produto na base da dados
        VagaDTO v = service.insert(vaga);

        //se inseriu
        assertNotNull(v);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = v.getId();
        assertNotNull(id);
        v = service.getVagaById(id);
        assertNotNull(v);

        //compara os valores inseridos com os valores pesquisados para confirmar
        assertEquals("Teste", v.getNome());
        assertEquals("Ben. do produto Teste", v.getBeneficios());
        assertEquals("Atrib Teste", v.getAtribuicoes());
        assertEquals("Horario de Teste", v.getHorario());
        assertEquals("Req de Teste", v.getRequisitos());

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        try {
            service.getVagaById(id);
            fail("O produto não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }

    @Test
    public void TestUpdate(){
        VagaDTO vDTO = service.getVagaById(1L);
        String nome = vDTO.getNome(); //armazena o valor original para voltar na base
        vDTO.setNome("Analista Modificado");
        Vaga v = Vaga.create(vDTO);

        vDTO = service.update(v, v.getId());
        assertNotNull(vDTO);
        assertEquals("Analista Modificado", vDTO.getNome());

        //volta ao valor original
        v.setNome(nome);
        vDTO = service.update(v, v.getId());
        assertNotNull(vDTO);
    }

    @Test
    public void testDelete(){
        //cria o produto para teste
        Vaga vaga = new Vaga();
        vaga.setNome("Teste");
        vaga.setBeneficios("Ben. do produto Teste");
        vaga.setAtribuicoes("Atrib Teste");
        vaga.setHorario("Horario de Teste");
        vaga.setRequisitos("Req de Teste");

        //insere o produto na base da dados
        VagaDTO v = service.insert(vaga);

        //se inseriu
        assertNotNull(v);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = v.getId();
        assertNotNull(id);
        v = service.getVagaById(id);
        assertNotNull(v);

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        try {
            service.getVagaById(id);
            fail("O produto não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }
}
