package com.example.study;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.sql.SQLException;

/**
 * 测试Mapper时候所需要的单独的组件
 * dbunit所需要的连接
 */
@TestConfiguration
public class TestDbunitMainConfig {

    @Value("${spring.datasource.url}")
    String urlStr ;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    /**
     * 创建dbunit所需要的数据源
     * @return
     */
    @Bean
    public DriverManagerDataSource driverManagerDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(urlStr,username,password);
        return dataSource;
    }

    /**
     * 创建dbunit操作数据库的连接
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public IDatabaseConnection dbunitConnection(DriverManagerDataSource dataSource) {
        IDatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection(dataSource.getConnection());
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return databaseConnection;
    }


}
