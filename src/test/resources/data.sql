insert into role(id,nome) values (1, 'ROLE_ADMIN');
insert into role(id,nome) values (2, 'ROLE_USER');

insert into user(id,nome,email,login,senha) values (1,'Admin','teste@gmail.com','admin','$2a$10$ZUU74FueW6Ed9aJTozw/0.3Eqc/n/kDOcl8P5dw2QJR9a7SaMNjki');
insert into user(id,nome,email,login,senha) values (2,'User','fulano@gmail.com','user','$2a$10$ZUU74FueW6Ed9aJTozw/0.3Eqc/n/kDOcl8P5dw2QJR9a7SaMNjki');

insert into user_roles(user_id,role_id) values(1, 1);
insert into user_roles(user_id,role_id) values(2, 2);

INSERT INTO vaga (id, nome, atribuicoes, beneficios, horario, requisitos) VALUES
(1, 'Analista de Redes', 'Analisar Redes', 'VT e VR', 'Das 08:00 as 17:00', 'Superior Completo'),
(2, 'Técnico de Redes', 'Manutenir Redes', 'VT e VR', 'Das 08:00 as 17:00', 'Técnico Completo'),
(3, 'Advogado', 'Participar de audiências', 'VT e VR', 'Das 09:00 as 16:00', 'Superior Completo'),
(4, 'Operador de Caixa', 'Receber Clientes e Operar Caixa', 'VT e VR', 'Das 14:00 as 22:00', 'Médio Completo'),
(5, 'Padeiro', 'Fazer pães e bolos', 'VT e VR', 'Das 05:00 as 14:00', 'Médio Completo');
