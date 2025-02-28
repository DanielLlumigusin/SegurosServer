-- Crear usuario administrador con todos los privilegios
CREATE USER '${ADMIN}'@'%' IDENTIFIED BY '${ADMIN_PASSWORD}';
GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${ADMIN}'@'%' WITH GRANT OPTION;

-- Crear usuario cliente con privilegios limitados
CREATE USER '${CLIENTE}'@'%' IDENTIFIED BY '${CLIENTE_PASSWORD}';
GRANT SELECT ON ${MYSQL_DATABASE}.* TO '${CLIENTE}'@'%';

FLUSH PRIVILEGES;
