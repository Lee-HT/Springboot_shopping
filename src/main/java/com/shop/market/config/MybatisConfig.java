//package com.shop.market.config;
//
//import javax.sql.DataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//
//@Configuration
//@MapperScan("org.mybatis.spring.sample.mapper")
//public class MybatisConfig {
//
//    @Bean
//    public DataSource dataSource(){
//        return new EmbeddedDatabaseBuilder().addScript("schema.sql").build();
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception{
//        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//
//        Resource[] resources = new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mapper/*.xml");
//
//        sessionFactory.setMapperLocations(resources);
//
//        return sessionFactory.getObject();
//    }
//}
