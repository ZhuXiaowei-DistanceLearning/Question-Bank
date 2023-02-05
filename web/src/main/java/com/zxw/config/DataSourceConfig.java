package com.zxw.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author zxw
 * @date 2021/10/11 10:25
 */
@Configuration
public class DataSourceConfig {


    @Bean
    public DataSourceProperties masterDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean
    public DataSourceProperties salve01DataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean
    public DataSourceProperties salve02DataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slave01DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave01")
    public DataSource slave01DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slave02DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave02")
    public DataSource slave02DataSource() {
        return DataSourceBuilder.create().build();
    }
}
