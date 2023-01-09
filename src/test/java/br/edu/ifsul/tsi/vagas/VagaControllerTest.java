package br.edu.ifsul.tsi.vagas;

import br.edu.ifsul.tsi.vagas.api.vagas.Vaga;
import br.edu.ifsul.tsi.vagas.api.vagas.VagaDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VagasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VagaControllerTest extends BaseAPITest{

    //Métodos utilitários
    private ResponseEntity<VagaDTO> getVaga(String url) {
        return get(url, VagaDTO.class);
    }

    private ResponseEntity<List<VagaDTO>> getVagas(String url) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<VagaDTO>>() {
                });
    }

    @Test
    public void selectAll() {
        List<VagaDTO> vagas = getVagas("/api/v1/vagas").getBody();
        assertNotNull(vagas);
        assertEquals(5, vagas.size());

        vagas = getVagas("/api/v1/vagas?page=0&size=5").getBody();
        assertNotNull(vagas);
        assertEquals(5, vagas.size());
    }

    @Test
    public void selectByNome() {

        assertEquals(1, getVagas("/api/v1/vagas/nome/Analista de Redes").getBody().size());
        assertEquals(1, getVagas("/api/v1/vagas/nome/Advogado").getBody().size());
        assertEquals(1, getVagas("/api/v1/vagas/nome/Padeiro").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getVaga("/api/v1/vagas/nome/xxx").getStatusCode());
    }

    @Test
    public void selectById() {

        assertNotNull(getVaga("/api/v1/vaga/1"));
        assertNotNull(getVaga("/api/v1/vaga/2"));
        assertNotNull(getVaga("/api/v1/vaga/3"));

        assertEquals(HttpStatus.NOT_FOUND, getVaga("/api/v1/vagas/1000").getStatusCode());
    }

    @Test
    public void testInsert() {

        Vaga vaga = new Vaga();
        vaga.setAtribuicoes("Blallalal");
        vaga.setBeneficios("VT e VR");
        vaga.setNome("Bombeiro");
        vaga.setHorario("Plantão");
        vaga.setRequisitos("Curso de Bombeiro");

        // Insert
        ResponseEntity response = post("/api/v1/vagas", vaga, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        VagaDTO v = getVaga(location).getBody();

        assertNotNull(v);
        assertEquals("Bombeiro", v.getNome());
        assertEquals("Curso de Bombeiro", v.getRequisitos());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getVaga(location).getStatusCode());
    }

    @Test
    public void testUpdate() {
        //primeiro insere o objeto
        Vaga vaga = new Vaga();
        vaga.setAtribuicoes("Lorem Ipsum");
        vaga.setBeneficios("VT e VR");
        vaga.setNome("Escritor");
        vaga.setHorario("08:00 as 19:00");
        vaga.setRequisitos("Curso de Letras");

        // Insert
        ResponseEntity response = post("/api/v1/vagas", vaga, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        VagaDTO v = getVaga(location).getBody();

        assertNotNull(v);
        assertEquals("Escritor", v.getNome());
        assertEquals("Curso de Letras", v.getRequisitos());

        //depois altera seu valor
        Vaga va = Vaga.create(v);
        va.setNome("Leitor");

        // Update
        response = put("/api/v1/vagas/" + v.getId(), va, null);
        System.out.println(response);
        assertEquals("Leitor", va.getNome());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getVaga(location).getStatusCode());

    }

    @Test
    public void testDelete() {
        this.testInsert();
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getVaga("/api/v1/vagas/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
