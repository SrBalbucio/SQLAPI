package balbucio.sqlapi.model.sql;

import balbucio.sqlapi.common.ISQL;
import balbucio.sqlapi.model.ColumnValue;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Table {

    private ISQL sql;
    private String tableName;
    private List<Column> columns;

    public Table(ISQL sql, String tableName){
        this.sql =  sql;
        this.tableName = tableName;
        Map<String, String> col = sql.getColumns(tableName);
        col.forEach((d, v) -> columns.add(new Column(sql, this,  d, v)));
    }
}
