INSERT INTO tb_roles(role_id, name) VALUES (1, 'admin');
INSERT INTO tb_roles(role_id, name) VALUES (2, 'basic');

-- PARA MYSQL
/*
-- Não insere se já existir no banco
INSERT IGNORE INTO tb_roles(role_id, name) VALUES (1, 'admin');
INSERT IGNORE INTO tb_roles(role_id, name) VALUES (2, 'basic');
 */