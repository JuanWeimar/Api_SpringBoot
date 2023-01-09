package br.edu.ifsul.tsi.vagas.api.infra.security.jwt;

import lombok.Data;
@Data
public class JwtLoginInput {
        private String username;
        private String password;
}
