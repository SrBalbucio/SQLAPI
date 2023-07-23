package balbucio.sqlapi.mysql;

import balbucio.sqlapi.common.SQLConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class MySQLConfig extends SQLConfig {

    private String IP;
    private int port;
    private String database;
    private String user;
    private String password;
}
