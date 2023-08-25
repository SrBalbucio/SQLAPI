package balbucio.sqlapi.common;

import lombok.Data;

@Data
public class SQLConfig {

    private int queryTimeout = 30;
    private int maxRows = 100;
}
