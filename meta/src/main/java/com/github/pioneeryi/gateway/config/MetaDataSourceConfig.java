package com.github.pioneeryi.gateway.config;

import com.github.pioneeryi.gateway.MetaDataSourceInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.github.pioneeryi.gateway.dao.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MetaDataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(MetaDataSourceConfig.class);

    @Autowired
    private MetaDataSourceInfo metaDataSourceInfo;

    @Bean
    public DataSource configDataSource() {
        DataSourceProperties dsProperties = new DataSourceProperties();
        dsProperties.setDriverClassName(metaDataSourceInfo.getDriver());
        dsProperties.setUrl(metaDataSourceInfo.getJdbcUrl());
        dsProperties.setUsername(metaDataSourceInfo.getUsername());
        dsProperties.setPassword(metaDataSourceInfo.getPassword());

        return dsProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 配置Mapper路径
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
            throws Exception {
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            return bean.getObject();
        } catch (Exception ex) {
            log.warn("schemaSqlSessionFactory exception,ex=", ex);
            throw new RuntimeException("schemaSqlSessionFactory init failure");
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        // 使用上面配置的Factory
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
