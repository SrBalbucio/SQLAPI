package balbucio.sqlapi.hikari;

import balbucio.sqlapi.common.ISQL;
import com.zaxxer.hikari.HikariConfig;

import java.sql.Connection;

public class HikariInstance extends ISQL {

    private HikariConfig config;
    private Connection connection;

    public HikariInstance(HikariConfig config){
        this.config = config;
    }

    @Override
    public void connect() {
        try{

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
