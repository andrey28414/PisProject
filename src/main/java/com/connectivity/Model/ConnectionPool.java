package com.connectivity.Model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class ConnectionPool {

    public static DataSource getDatasource() {
        HikariConfig config = new HikariConfig();
        config.setUsername("user1");
        config.setPassword("aeNFK183afj8NFK8dnamf8DFSsf8371~/dnsa/sDJ28+FNVJS213");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/Books");
        DataSource ds = new HikariDataSource(config);
        return ds;
    }

}
