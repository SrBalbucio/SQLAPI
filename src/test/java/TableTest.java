import balbucio.sqlapi.sqlite.SQLiteInstance;
import balbucio.sqlapi.sqlite.SqliteConfig;

import java.io.File;

public class TableTest {

    public static void main(String[] args) {
        SqliteConfig config = new SqliteConfig(new File("database.db"));
        config.createFile();
        SQLiteInstance instance = new SQLiteInstance(config);
        instance.createTable("TI", "aluno VARCHAR(255), id BIGINT");
        instance.createTable("INGLES", "aluno VARCHAR(255), id BIGINT");
        instance.createTable("MEDIO", "aluno VARCHAR(255), id BIGINT");
        System.out.println(instance.getTableNames());
    }
}
