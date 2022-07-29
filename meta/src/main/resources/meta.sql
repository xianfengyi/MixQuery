CREATE TABLE t_meta_calcite_schema (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) NOT NULL COMMENT 'schema名',
   `type` varchar(255) NOT NULL DEFAULT '' COMMENT 'schema类型',
   `factory` varchar(255) NOT NULL COMMENT 'jdbc factory',
   `operand` varchar(255) NOT NULL COMMENT 'schema operand',
   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;