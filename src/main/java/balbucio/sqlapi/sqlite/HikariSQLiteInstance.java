package balbucio.sqlapi.sqlite;

import balbucio.sqlapi.hikari.HikariInstance;
import balbucio.sqlapi.sqlserver.SQLServerConfig;
import com.zaxxer.hikari.HikariConfig;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class HikariSQLiteInstance extends HikariInstance {

    public HikariSQLiteInstance(SqliteConfig sqlServerConfig){
        this(new HikariConfig(), sqlServerConfig);
    }

    /**
     * Crie usando o HikariConfig e o SQLConfig
     *
     * @param hk
     * @param sqlconfig
     */
    public HikariSQLiteInstance(HikariConfig hk, SqliteConfig sqlconfig) {
        this.sqlConfig = sqlconfig;
        if(sqlconfig.isMemory()){
            hk.setJdbcUrl("jdbc:sqlite:"+sqlconfig.getId()+"?mode=memory&cache=shared");
        } else {
            hk.setJdbcUrl("jdbc:sqlite:"+sqlconfig.getDatabaseFile().getAbsolutePath());
        }
        hk.setDriverClassName("org.sqlite.JDBC");
        setHikariConfig(hk);
    }

    @Override
    public void replace(String columns, String values, String tableName) {
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("INSERT OR IGNORE INTO "+tableName+" ("+columns+") VALUES ("+values+");");
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
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
