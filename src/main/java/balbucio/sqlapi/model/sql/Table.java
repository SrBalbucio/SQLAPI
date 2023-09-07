package balbucio.sqlapi.model.sql;

import balbucio.sqlapi.common.ISQL;
import balbucio.sqlapi.model.ColumnValue;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

@Data
public class Table {

    private ISQL sql;
    private String tableName;
    private List<Column> columns;

    public Table(ISQL sql, String tableName) {
        this.sql = sql;
        this.tableName = tableName;
        Map<String, String> col = sql.getColumns(tableName);
        col.forEach((d, v) -> columns.add(new Column(sql, this, d, v)));
    }

    public Column getColumn(String name) {
        return columns.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean toCSV(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileWriter writer = new FileWriter(file);

            String[] header = new String[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                header[i] = columns.get(i).getName();
            }

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .setHeader(header)
                    .build();
            final CSVPrinter printer = new CSVPrinter(writer, csvFormat);
            for (Object[] objs : sql.getAllValuesFromColumnsToObj(tableName)) {
                printer.print(objs);
            }
            writer.flush();

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
