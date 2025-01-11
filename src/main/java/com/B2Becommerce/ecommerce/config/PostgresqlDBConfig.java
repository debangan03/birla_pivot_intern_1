package com.B2Becommerce.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PostgresqlDBConfig {

    //store values or the credentials
    private final String DRIVER_CLASSNAME="org.postgresql.Driver";
    private final String URL="jdbc:postgresql://localhost:5432/postgres";
    private final String USER_NAME ="postgres";
    private final String PASSWORD="10102003";

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // Set PostgresSQL database connection properties
        dataSource.setDriverClassName(DRIVER_CLASSNAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }
}
