INSERT INTO tb_roles(role_id, name) VALUES (1, 'ADMIN');
INSERT INTO tb_roles(role_id, name) VALUES (2, 'BASIC');

-- PARA MYSQL
/*
-- Não insere se já existir no banco
INSERT IGNORE INTO tb_roles(role_id, name) VALUES (1, 'ADMIN');
INSERT IGNORE INTO tb_roles(role_id, name) VALUES (2, 'BASIC');
 */