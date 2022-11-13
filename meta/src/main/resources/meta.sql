CREATE TABLE t_meta_calcite_schema (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) NOT NULL COMMENT 'schema名',
   `type` varchar(255) NOT NULL DEFAULT '' COMMENT 'schema类型',
   `factory` varchar(255) NOT NULL COMMENT 'jdbc factory',
   `operand` varchar(255) NOT NULL COMMENT 'schema operand',
   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `t_meta_calcite_schema` (`id`, `name`, `type`, `factory`, `operand`, `update_time`) VALUES (1, 'db1', 'custom', 'org.apache.calcite.adapter.jdbc.JdbcSchema$Factory', '{\"jdbcDriver\": \"com.mysql.cj.jdbc.Driver\",\"jdbcUrl\": \"jdbc:mysql://127.0.0.1:3306/test\",\"jdbcUser\": \"root\",\"jdbcPassword\": \"Yxf209831\"}', '2022-09-03 22:17:34');
INSERT INTO `t_meta_calcite_schema` (`id`, `name`, `type`, `factory`, `operand`, `update_time`) VALUES (2, 'db2', 'custom', 'org.apache.calcite.adapter.jdbc.JdbcSchema$Factory', '{\"jdbcDriver\": \"org.postgresql.Driver\",\"jdbcUrl\": \"jdbc:postgresql://localhost:5432/test\",\"jdbcUser\": \"postgres\",\"jdbcPassword\": \"123456\"}', '2022-09-03 22:51:12');