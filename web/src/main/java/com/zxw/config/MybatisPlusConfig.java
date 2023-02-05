package com.zxw.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxw
 * @date 2021/9/13 17:23
 */
@Configuration
public class MybatisPlusConfig {
    @Autowired
    private DataSource masterDataSource;

    @Autowired
    private DataSource slave01DataSource;

    @Autowired
    private DataSource slave02DataSource;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 设置事务，事务需要知道当前使用的是哪个数据源才能进行事务处理
     *
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() throws Exception {
        return new DataSourceTransactionManager(routingDataSource());
    }

    @Bean
    @Primary
    public DataSourceRouter routingDataSource() throws Exception {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource);
        targetDataSources.put("slave01", slave01DataSource);
        targetDataSources.put("slave02", slave02DataSource);
        DataSourceRouter routingDataSource = new DataSourceRouter();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

    //    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); // 使用mybatis的全局配置文件
        //使用mybatisplus的工程bean，mybatis-plus的全局配置文件才会生效
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        // 扫描model
        sqlSessionFactoryBean.setTypeAliasesPackage("com.zxw");
        // 扫描映射文件
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/**/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }


    @Autowired
    MybatisPlusInterceptor mybatisPlusInterceptor;

}
