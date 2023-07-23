package balbucio.sqlapi.h2;

import balbucio.sqlapi.hikari.HikariInstance;
import balbucio.sqlapi.model.ConditionValue;
import balbucio.sqlapi.sqlite.SqliteConfig;
import com.zaxxer.hikari.HikariConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class HikariH2Instance extends HikariInstance {

    public HikariH2Instance(H2Config sqlServerConfig){
        super(new HikariConfig(), sqlServerConfig);
    }

    /**
     * Crie usando o HikariConfig e o SQLConfig
     *
     * @param hk
     * @param sqlconfig
     */
    public HikariH2Instance(HikariConfig hk, H2Config sqlconfig) {
        this.sqlConfig = sqlconfig;
        hk.setJdbcUrl("jdbc:sqlite:" + sqlconfig.getDatabaseFile().getAbsolutePath());
        hk.setUsername(sqlconfig.getUser());
        hk.setPassword(sqlconfig.getPassword());
        hk.setDriverClassName("org.h2.Driver");
        setHikariConfig(hk);
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
