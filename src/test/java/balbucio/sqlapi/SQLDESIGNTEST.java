package balbucio.sqlapi;

import balbucio.sqlapi.model.ConditionValue;
import balbucio.sqlapi.model.Conditional;
import balbucio.sqlapi.model.Operator;
import balbucio.sqlapi.model.sql.Column;
import balbucio.sqlapi.model.sql.Table;
import balbucio.sqlapi.sqlite.HikariSQLiteInstance;
import balbucio.sqlapi.sqlite.SqliteConfig;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;

public class SQLDESIGNTEST {

    @BeforeAll
    public void init(){
        SqliteConfig config  = new SqliteConfig(new File("database2.db"));
        config.recreateFile();
        HikariSQLiteInstance instance = new HikariSQLiteInstance(config);
        Table table = instance.createTable("produtos", "name VARCHAR(255), price DOUBLE");
        if(table != null) {
            Column column = table.getColumn("price");
            double price =
                    (double) column.get(new ConditionValue("name", Conditional.EQUALS, "Phone", Operator.NULL));
        }
    }
}