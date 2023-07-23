package balbucio.sqlapi.model;

import java.util.Map;

public class ColumnValue implements Map.Entry{

    private String key;
    private Object value;
    @Override
    public Object getKey() {
        return key;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object setValue(Object value) {
        this.value = value;
        return value;
    }
}
