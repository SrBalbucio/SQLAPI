package balbucio.sqlapi.hikari;

import balbucio.sqlapi.common.ISQL;
import balbucio.sqlapi.common.SQLConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class HikariInstance extends ISQL {

    protected HikariConfig config;
    protected SQLConfig sqlConfig;
    private HikariDataSource source;
    private Connection connection;

    /**
     * Crie usando o HikariConfig e o SQLConfig
     * @param config
     * @param sqlconfig
     */
    public HikariInstance(HikariConfig config, SQLConfig sqlconfig){
        this.sqlConfig = sqlconfig;
        setHikariConfig(config);
    }

    public void setHikariConfig(HikariConfig config){
        try {
            if (isConnected()) {
                source.close();
                connection.close();
                connection = null;
            }
            this.config = config;
            this.source = new HikariDataSource(config);
        } catch (Exception e){
            e.printStackTrace();
        }
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
        Map<String, String> colu = new HashMap<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while (columns.next()) {
                colu.put(columns.getString("COLUMN_NAME"), columns.getString("TYPE_NAME"));
            }

            columns.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colu;
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

    @Override
    public void closeConnection() {
        source.close();

    }
}
