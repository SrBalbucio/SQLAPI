import balbucio.sqlapi.sqlite.SQLiteInstance;
import balbucio.sqlapi.sqlite.SqliteConfig;

import java.io.File;

public class TracoTeste {

    public static void main(String[] args) {
        SqliteConfig config = new SqliteConfig(new File("database.db"));
        config.createFile();
        SQLiteInstance instance = new SQLiteInstance(config);
        instance.createTable("grupos", "`group-name` VARCHAR(255), id BIGINT");
        instance.insert("`group-name`, id", "'King', '1'", "grupos");
        instance.insert("`group-name`, id", "'Hunter', '2'", "grupos");
        instance.insert("`group-name`, id", "'Prince', '3'", "grupos");
    }
}
