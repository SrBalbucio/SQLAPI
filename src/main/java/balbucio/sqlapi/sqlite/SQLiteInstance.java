package balbucio.sqlapi.sqlite;

import balbucio.sqlapi.common.ISQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Retorna a conexão atual
     * @return
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Map<String, String> getColumns(String tableName) {
        Map<String, String> columns = new HashMap<>();
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            ResultSet set = statement.executeQuery("PRAGMA table_info(" + tableName + ")");

            while (set.next()){
                columns.put(set.getString("name"), set.getString("type"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return columns;
    }
}
