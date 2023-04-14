package balbucio.sqliteapi;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteInstance {

    private SqliteConfig config;
    private Connection connection = null;

    public SQLiteInstance(SqliteConfig config){
        this.config = config;
    }

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
    
    public Statement getStatement() throws SQLException {
        Statement set = connection.createStatement();
        set.setQueryTimeout(config.getQueryTimeout());
        set.setMaxRows(config.getMaxRows());
        return set;
    }

    public boolean isConnected(){
        try {
            return connection != null && connection.isValid(100);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void createTable(String tableName, String columns){
        try {
            if (!isConnected()) {
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+tableName+" ("+columns+");");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean tableExists(String table){
        boolean exists = false;
        try {
            if (!isConnected()) {
                connect();
            }

            DatabaseMetaData data = connection.getMetaData();
            ResultSet set = data.getTables(null, null, table, null);
            if(set == null)
                exists = false;
            if(set.next())
                exists = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return exists;
    }

    public List<String> getTableNames(){
        List<String> tables = new ArrayList<>();
        try{
            if(!isConnected()){
                connect();
            }

            DatabaseMetaData data =  connection.getMetaData();
            ResultSet set = data.getTables(null, null, "%", null);
            while(set.next()){
               tables.add(set.getString(3));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return tables;
    }

    public void truncateTable(String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("TRUNCATE TABLE "+tableName+";");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insert(String columns, String values, String tableName){
        try{
             if(!isConnected()){
                 connect();
             }

            Statement statement = getStatement();
            statement.executeUpdate("INSERT INTO "+tableName+" ("+columns+") VALUES ("+values+");");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void upsert(String columns, String values, String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("INSERT IGNORE INTO "+tableName+" ("+columns+") VALUES ("+values+");");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void set(String columnSelected, String logic, String data, String selected,  Object newValue, String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("UPDATE "+tableName+" SET "+selected+" = "+newValue+" WHERE "+columnSelected+" "+logic+" "+data+";");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
    public void replace(String columns, String values, String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("")
        } catch (Exception e){
            e.printStackTrace();
        }
    }
     **/

    public Object get(String columnSelected, String logic, String data, String selected, String tableName){
        Object obj = null;
        try{
            Statement statement = getStatement();
            ResultSet set = statement.executeQuery("SELECT "+selected+" FROM "+tableName+" WHERE "+columnSelected+" "+logic+" "+data+";");
            while (set.next()){
                obj = set.getObject(selected);
            }
        } catch (Exception e){
            e.printStackTrace();

        }
        return obj;
    }

    public List<Object> getAll(String columnSelected, String logic, String data, String selected, String tableName){
        List<Object> objects = new ArrayList<>();
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            ResultSet set = statement.executeQuery("SELECT "+selected+" FROM "+tableName+" WHERE "+columnSelected+" "+logic+" "+data+";");
            while(set.next()){
                objects.add(set.getObject(selected));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return objects;
    }

    public List<Object[]> getAllValuesFromColumns(String tableName, String... columns){
        List<Object[]> objs = new ArrayList<>();
        try{
            Statement statement = getStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM "+tableName);
            while(set.next()){
                Object[] values = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    values[i] = set.getObject(columns[i]);
                }
                objs.add(values);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return objs;
    }
    
    public ResultSet query(String query){
        ResultSet set = null;
        try{
            Statement statement = getStatement();
            set = statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return set;
    }
}
