package balbucio.sqlapi.h2;

import balbucio.sqlapi.common.ISQL;
import balbucio.sqlapi.model.ConditionValue;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class H2Instance extends ISQL {

    private H2Config config;
    private Connection connection = null;

    public H2Instance(H2Config config) {
        this.config = config;
    }

    @Override
    public void connect() {
        try{
            connection = DriverManager.getConnection("jdbc:h2:"+config.getDatabaseFile().getAbsolutePath(), config.getUser(), config.getPassword());
        } catch (Exception e){
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
        Map<String, String> values = new HashMap<>();
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+tableName);
            ResultSetMetaData metaData = resultSet.getMetaData();

            int numeroColunas = metaData.getColumnCount();

            for (int i = 1; i <= numeroColunas; i++) {
                values.put(metaData.getColumnName(i), metaData.getColumnTypeName(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public boolean exists(ConditionValue[] values, String tableName) {
        boolean exist = false;
        try{
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName + " WHERE "+getConditionQuery(values));
            resultSet.next();
            int count = resultSet.getInt(1);
            exist = count>0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public boolean exists(ConditionValue value, String tableName) {
        boolean exist = false;
        try{
            PreparedStatement statement = getPreparedStatement("SELECT COUNT(*) FROM " + tableName + " WHERE "+value.getColumn()+" "+value.getConditional().getValue()+" ?");
            statement.setObject(1, value.getValue());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            exist = count>0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public boolean exists(String column, String condition, Object item, String tableName) {
        boolean exist = false;
        try{
            PreparedStatement statement = getPreparedStatement("SELECT COUNT(*) FROM " + tableName + " WHERE " +column+ " "+condition+" ?");
            statement.setObject(1, item);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            exist = count>0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return exist;
    }
}
