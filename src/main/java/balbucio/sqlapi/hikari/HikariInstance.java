package balbucio.sqlapi.hikari;

import balbucio.sqlapi.common.ISQL;
import balbucio.sqlapi.common.SQLConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariInstance extends ISQL {

    private HikariConfig config;
    private SQLConfig sqlConfig;
    private HikariDataSource source;
    private Connection connection;

    public HikariInstance(HikariConfig config, SQLConfig sqlconfig){
        this.sqlConfig = sqlconfig;
        this.config = config;
        this.source = new HikariDataSource(config);
    }

    @Override
    public void connect() {
        try{
            this.connection = source.getConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Statement getStatement() throws SQLException {
        Statement set = connection.createStatement();
        set.setQueryTimeout(sqlConfig.getQueryTimeout());
        set.setMaxRows(sqlConfig.getMaxRows());
        return set;
    }
}
