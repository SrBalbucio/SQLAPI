package balbucio.sqlapi.hikari;

import balbucio.sqlapi.common.ISQL;
import balbucio.sqlapi.common.SQLConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class HikariInstance extends ISQL {

    private HikariConfig config;
    private SQLConfig sqlConfig;
    private HikariDataSource source;
    private Connection connection;

    /**
     * Crie usando o HikariConfig e o SQLConfig
     * @param config
     * @param sqlconfig
     */
    public HikariInstance(HikariConfig config, SQLConfig sqlconfig){
        this.sqlConfig = sqlconfig;
        this.config = config;
        this.source = new HikariDataSource(config);
    }

    /**
     * Cria uma conexão Hikari
     */
    @Override
    public void connect() {
        try{
            this.connection = source.getConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
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
        return null;
    }

    /**
     * Crie um statement configurado
     * @return retorna o Statement
     * @throws SQLException em caso de erro
     */
    @Override
    public Statement getStatement() throws SQLException {
        Statement set = connection.createStatement();
        set.setQueryTimeout(sqlConfig.getQueryTimeout());
        set.setMaxRows(sqlConfig.getMaxRows());
        return set;
    }

    @Override
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        PreparedStatement set = connection.prepareStatement(sql);
        set.setQueryTimeout(sqlConfig.getQueryTimeout());
        set.setMaxRows(sqlConfig.getMaxRows());
        return set;
    }
}
