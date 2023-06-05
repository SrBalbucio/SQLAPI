package balbucio.sqlapi;

import balbucio.sqlapi.sqlite.SQLiteInstance;
import balbucio.sqlapi.sqlite.SqliteConfig;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SQLITETEST {

    SqliteConfig config = new SqliteConfig(new File("database.db"));
    SQLiteInstance instance;
    int i = 1;

    @BeforeAll
    public void init(){
        config.recreateFile();
        instance = new SQLiteInstance(config);
        instance.createTable("TI", "aluno VARCHAR(255), id BIGINT");
        instance.insert("aluno, id", "'JoaoMaisLindo', '1'", "TI");
        instance.insert("aluno, id", "'TawanGostoso', '2'", "TI");
        instance.insert("aluno, id", "'MARCIOTRANSADO', '3'", "TI");
        instance.createTable("INGLES", "aluno VARCHAR(255), id BIGINT");
        instance.createTable("MEDIO", "aluno VARCHAR(255), id BIGINT");
        instance.createTable("FACULDADE", "aluno VARCHAR(255), id BIGINT");
        instance.createTable("grupos", "`group-name` VARCHAR(255), id BIGINT");
    }

    @DisplayName("Criando grupos com traço")
    @Test
    public void getGroup(){
        instance.insert("`group-name`, id", "'King', '1'", "grupos");
        instance.insert("`group-name`, id", "'Hunter', '2'", "grupos");
        instance.insert("`group-name`, id", "'Prince', '3'", "grupos");
        assertEquals("King",instance.get("id", "=", "1", "`group-name`", "grupos"), "Retornar King");
        assertEquals("Hunter",instance.get("id", "=", "2", "`group-name`", "grupos"), "Retornar Hunter");
        assertEquals("Prince",instance.get("id", "=", "3", "`group-name`", "grupos"), "Retornar Prince");
    }

    @DisplayName("Pegando usuários do banco de dados")
    @Test
    @RepeatedTest(3)
    public void getValues(){
        assertNotEquals(null, instance.get("id", "=", String.valueOf(i), "aluno", "TI"));
    }


    @DisplayName("Recuperando as informações de tabela")
    @Test
    public void getTables(){
        assertTrue(!instance.getTableNames().isEmpty(), "Verificando se as tabelas retornaram");
    }

    @DisplayName("Recuperando as informações de coluna")
    @Test
    public void getColumns(){
        assertTrue(!instance.getColumns("grupos").isEmpty());
    }

    @DisplayName("Checar de tabela existe")
    @Test
    public void tableExists(){
        assertTrue(instance.tableExists("grupos"));
    }

    @DisplayName("Deletar item")
    @Test
    public void delete(){
        instance.delete("id", 1, "grupos");
        assertNull(instance.get("id", "=", "1", "`group-name`", "grupos"));
    }

    @DisplayName("Checar se existe")
    @Test
    public void existsNot(){
        assertFalse(instance.exists("id", "=", "4", "grupos"));
    }
    @DisplayName("Checar se existe")
    @Test
    public void exists(){
        instance.insert("`group-name`, id", "'Hunter', '2'", "grupos");
        assertTrue(instance.exists("id", "=", "2", "grupos"));
    }
}
