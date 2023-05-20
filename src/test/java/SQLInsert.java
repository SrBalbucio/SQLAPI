import balbucio.sqlapi.sqlite.SQLiteInstance;
import balbucio.sqlapi.sqlite.SqliteConfig;

import java.io.File;

public class SQLInsert {

    public static void main(String[] args) {
        SqliteConfig config = new SqliteConfig(new File("database.db"));
        config.createFile();
        SQLiteInstance instance = new SQLiteInstance(config);
        instance.createTable("TI", "aluno VARCHAR(255), id BIGINT");
        instance.insert("aluno, id", "'JoaoMaisLindo', '1'", "TI");
        instance.insert("aluno, id", "'TawanGostoso', '2'", "TI");
        instance.insert("aluno, id", "'MARCIOTRANSADO', '3'", "TI");
        System.out.println(instance.get("id", "=", "1", "aluno", "TI"));
        instance.getAllValuesFromColumns("TI", "aluno", "id").forEach(o -> {
            for (int i = 0; i < o.length; i++) {
                System.out.println(o[i]);
            }
        });
    }
}
