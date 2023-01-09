package br.edu.ifsul.tsi.vagas.api.vagas;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/vagas")
//@Api(value = "Vagas")

public class VagaController {
    @Autowired
    private VagaService service;

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    //@ApiOperation(value = "Retorna todos os produtos cadastrados.")
    public ResponseEntity<List<VagaDTO>> selectAll() {
        List<VagaDTO> vagas = service.getVagas();
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("{id}")
//    @ApiOperation(value = "Retorna um produto pelo campo identificador.")
    public ResponseEntity<VagaDTO> selectById(@PathVariable("id") Long id) {
        VagaDTO vaga = service.getVagaById(id);
        return ResponseEntity.ok(vaga);
    }

    @GetMapping("/nome/{nome}")
//    @ApiOperation(value = "Retorna uma lista de produtos pela chave nome.")
    public ResponseEntity<List<VagaDTO>> selectByNome(@PathVariable("nome") String nome) {
        List<VagaDTO> vagas = service.getVagasByNome(nome);
        return vagas.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(vagas);
    }

    @PostMapping
//    @Secured({"ROLE_ADMIN"})
//    @ApiOperation(value = "Insere um novo produto.")
    public ResponseEntity<String> insert(@RequestBody Vaga vaga){
        VagaDTO p = service.insert(vaga);
        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
//    @ApiOperation(value = "Altera um produto existente.")
    public ResponseEntity<VagaDTO> update(@PathVariable("id") Long id, @RequestBody Vaga vaga){
        vaga.setId(id);
        VagaDTO p = service.update(vaga, id);
        return p != null ?
                ResponseEntity.ok(p) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
//    @ApiOperation(value = "Deleta um produto.")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    //utilitário
    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
