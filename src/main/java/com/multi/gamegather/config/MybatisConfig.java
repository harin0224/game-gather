package com.multi.gamegather.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// 이 클래스는 MyBatis 설정을 담당합니다.
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.multi.gamegather", annotationClass = Mapper.class)
public class MybatisConfig {

    // 데이터 소스 트랜잭션 매니저를 설정합니다.
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // SQL 세션 팩토리를 설정합니다.
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource datasource) throws Exception{
//        SqlSessionFactoryBean seb  = new SqlSessionFactoryBean();
//        // 클래스 패스 내의 mapper 파일을 로드합니다.
//        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/**/*.xml");
//
//        seb.setMapperLocations(res);
//
//        seb.setDataSource(datasource);
//        return seb.getObject();
//    }

}