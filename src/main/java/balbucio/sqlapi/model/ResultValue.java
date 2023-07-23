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

}
