package balbucio.sqlapi.h2;

import balbucio.sqlapi.hikari.HikariInstance;
import balbucio.sqlapi.sqlite.SqliteConfig;
import com.zaxxer.hikari.HikariConfig;

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
}
