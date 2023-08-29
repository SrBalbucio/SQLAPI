package balbucio.sqlapi.model.sql;

import balbucio.sqlapi.common.ISQL;
import balbucio.sqlapi.model.ConditionValue;
import balbucio.sqlapi.model.Conditional;
import balbucio.sqlapi.model.Operator;
import lombok.Data;

@Data
public class Column {

    private ISQL sql;
    private Table fromTable;
    private String name;
    private String type;

    public Column(ISQL sql, Table fromTable, String name, String type) {
        this.sql = sql;
        this.fromTable = fromTable;
        this.name = name;
        this.type = type;
    }

    public boolean exists(ConditionValue condition){
        return sql.exists(condition, fromTable.getTableName());
    }

    public void set(ConditionValue condition, Object value){
        sql.set(condition, name, value, fromTable.getTableName());
    }

    public Object get(ConditionValue condition){
        return sql.get(condition, name, fromTable.getTableName());
    }
}
