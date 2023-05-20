package balbucio.sqlapi.sqlite;

import balbucio.sqlapi.common.ISQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteInstance extends ISQL {

    private SqliteConfig config;
    private Connection connection = null;

    /**
     * Crie usando o SqliteConfig
     * @param config
     */
    public SQLiteInstance(SqliteConfig config){
        this.config = config;
    }

    /**
     * Cria uma conexão SQLite
     */
    @Override
    public void connect(){
        try{
            if(config.isMemory()){
                connection = DriverManager.getConnection("jdbc:sqlite:"+config.getId()+"?mode=memory&cache=shared");
            } else {
                connection = DriverManager.getConnection("jdbc:sqlite:"+config.getDatabaseFile().getAbsolutePath());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Crie um statement configurado
     * @return retorna o Statement
     * @throws SQLException em caso de erro
     */
    @Override
    public Statement getStatement() throws SQLException {
        Statement set = connection.createStatement();
        set.setQueryTimeout(config.getQueryTimeout());
        set.setMaxRows(config.getMaxRows());
        return set;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
