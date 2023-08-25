package balbucio.sqlapi.msaccess;

import balbucio.sqlapi.common.ISQL;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AccessInstance extends ISQL {

    private AccessConfig config;
    private Connection connection;

    public AccessInstance(AccessConfig config) {
        this.config = config;
    }

    @Override
    public void connect() {
        try{
            this.connection = DriverManager.getConnection("jdbc:ucanaccess://"+config.getFile().getAbsolutePath()+ (config.isMemory() ? ";memory=false" : ""));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Statement getStatement() throws SQLException {
        Statement set = connection.createStatement();
        set.setQueryTimeout(config.getQueryTimeout());
        set.setMaxRows(config.getMaxRows());
        return set;
    }

    @Override
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        PreparedStatement set = connection.prepareStatement(sql);
        set.setQueryTimeout(config.getQueryTimeout());
        set.setMaxRows(config.getMaxRows());
        return set;
    }

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
}
