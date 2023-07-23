package balbucio.sqlapi.mariadb;

import balbucio.sqlapi.common.SQLConfig;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class MariaDbConfig extends SQLConfig {

    private String IP;
    private int port;
    private String database;
    private String user;
    private String password;


}
