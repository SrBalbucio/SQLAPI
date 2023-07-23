package balbucio.sqlapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultValue {

    private String tableName;
    private Map<String, Object> valueByColumn = new HashMap<>();

    public boolean has(String column){
        return valueByColumn.containsKey(column);
    }
    public Object get(String column){
        return valueByColumn.get(column);
    }

    public String asString(String column){
        return (String) get(column);
    }

    public Boolean asBool(String column){
        return (boolean) get(column);
    }

    public int asInt(String column){
        return (int) get(column);
    }

    public long asLong(String column){
        return (long) get(column);
    }

}
