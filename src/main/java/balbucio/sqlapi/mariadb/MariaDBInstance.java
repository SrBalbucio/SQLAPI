package balbucio.sqlapi.mariadb;

import balbucio.sqlapi.hikari.HikariInstance;
import balbucio.sqlapi.sqlserver.SQLServerConfig;
import com.zaxxer.hikari.HikariConfig;

public class MariaDBInstance extends HikariInstance {

    public MariaDBInstance(MariaDbConfig sqlServerConfig){
        super(new HikariConfig(), sqlServerConfig);
    }

    /**
     * Crie usando o HikariConfig e o SQLConfig
     *
     * @param hk
     * @param sqlconfig
     */
    public MariaDBInstance(HikariConfig hk, MariaDbConfig sqlconfig) {
        this.sqlConfig = sqlconfig;
        hk.setJdbcUrl("jdbc:mariadb://"+sqlconfig.getIP()+":"+sqlconfig.getPort()+"/"+sqlconfig.getDatabase()+";");
        hk.setUsername(sqlconfig.getUser());
        hk.setPassword(sqlconfig.getPassword());
        hk.setDriverClassName("org.mariadb.jdbc.Driver");
        setHikariConfig(hk);
    }
}
