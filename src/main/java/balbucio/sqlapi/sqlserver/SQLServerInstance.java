package balbucio.sqlapi.sqlserver;

import balbucio.sqlapi.common.SQLConfig;
import balbucio.sqlapi.hikari.HikariInstance;
import com.zaxxer.hikari.HikariConfig;

public class SQLServerInstance extends HikariInstance {

    public SQLServerInstance(SQLServerConfig sqlServerConfig){
        super(new HikariConfig(), sqlServerConfig);
    }

    /**
     * Crie usando o HikariConfig e o SQLConfig
     *
     * @param hk
     * @param sqlconfig
     */
    public SQLServerInstance(HikariConfig hk, SQLServerConfig sqlconfig) {
        this.sqlConfig = sqlconfig;
        hk.setJdbcUrl("jdbc:sqlserver://"+sqlconfig.getIP()+":"+sqlconfig.getPort()+";databaseName="+sqlconfig.getDatabase()+";");
        hk.setUsername(sqlconfig.getUser());
        hk.setPassword(sqlconfig.getPassword());
        hk.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        setHikariConfig(hk);
    }
}
