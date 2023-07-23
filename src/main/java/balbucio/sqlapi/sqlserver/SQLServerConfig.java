package balbucio.sqlapi.sqlserver;

import balbucio.sqlapi.common.SQLConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SQLServerConfig extends SQLConfig {

    private String IP;
    private int port;
    private String database;
    private String user;
    private String password;
}
