package br.edu.ifsul.tsi.vagas.api.vagas;

import lombok.Data;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
public class VagaDTO {
    private Long id;
    private String nome;
    private String atribuicoes;
    private String requisitos;
    private String horario;
    private String beneficios;

    public static VagaDTO create(Vaga v){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(v, VagaDTO.class);
    }
}
