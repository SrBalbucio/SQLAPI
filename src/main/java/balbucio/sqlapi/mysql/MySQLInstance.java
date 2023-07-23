package balbucio.sqlapi.mysql;

import balbucio.sqlapi.hikari.HikariInstance;
import balbucio.sqlapi.sqlserver.SQLServerConfig;
import com.zaxxer.hikari.HikariConfig;

public class MySQLInstance extends HikariInstance {

    public MySQLInstance(MySQLConfig sqlServerConfig){
        super(new HikariConfig(), sqlServerConfig);
    }

    /**
     * Crie usando o HikariConfig e o SQLConfig
     *
     * @param hk
     * @param sqlconfig
     */
    public MySQLInstance(HikariConfig hk, MySQLConfig sqlconfig) {
        this.sqlConfig = sqlconfig;
        hk.setJdbcUrl("jdbc:mysql://"+sqlconfig.getIP()+":"+sqlconfig.getPort()+"/"+sqlconfig.getDatabase()+";");
        hk.setUsername(sqlconfig.getUser());
        hk.setPassword(sqlconfig.getPassword());
        hk.setDriverClassName("com.mysql.cj.jdbc.Driver");
        setHikariConfig(hk);
    }
}
