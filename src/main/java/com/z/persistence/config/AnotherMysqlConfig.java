package com.z.persistence.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * MysqlConfig
 *
 * @author Lee
 * @date 2023/2/26
 */
@Configuration
@MapperScan(basePackages = "com.z.persistence.mysql.p2.dao", sqlSessionFactoryRef = "anotherMysqlSqlSessionFactory")
public class AnotherMysqlConfig {


    @ConfigurationProperties(prefix = "spring.datasource.another")
    @Bean("anotherMysqlDataSource")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("anotherMysqlSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("anotherMysqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/p2/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }


    @Bean("anotherMysqlSqlSessionFactory")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("anotherMysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("anotherMysqlTransactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("anotherMysqlDataSource") DataSource dataSource
    ) {
        return new DataSourceTransactionManager(dataSource);
    }
}
