package br.edu.ifsul.tsi.vagas.api.vagas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga,Long> {
    List<Vaga> findByNome(String nome);
}
