package br.edu.ifsul.tsi.vagas.api.vagas;

import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String atribuicoes;
    private String requisitos;
    private String horario;
    private String beneficios;

    public static Vaga create(VagaDTO v){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(v, Vaga.class);
    }
}
