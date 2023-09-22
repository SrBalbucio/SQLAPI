package balbucio.sqlapi;

import balbucio.sqlapi.model.ConditionValue;
import balbucio.sqlapi.model.Conditional;
import balbucio.sqlapi.model.Operator;
import balbucio.sqlapi.model.ResultValue;
import balbucio.sqlapi.sqlite.SQLiteInstance;
import balbucio.sqlapi.sqlite.SqliteConfig;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

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
        instance.createTable("Produtos", "name VARCHAR(255), price DOUBLE");
        instance.insert("name, price", "'Smartphone Salung 5', '5200'", "Produtos");
        instance.insert("name, price", "'Smartphone Xialong', '2200'", "Produtos");
        instance.insert("name, price", "'Smartphone Negativo 2 MAX', '500'", "Produtos");
        instance.insert("name, price", "'Smartphone Pera 12 PRO MAX', '90200'", "Produtos");
        instance.insert("name, price", "'Smartphone Azus 9', '3200'", "Produtos");
        instance.insert("name, price", "'Smartphone FakeMe 9', '2700'", "Produtos");
        instance.insert("name, price", "'Notebook lled', '6700'", "Produtos");
        instance.insert("name, price", "'Notebook Salung', '5700'", "Produtos");
        instance.insert("name, price", "'Notebook PH', '7700'", "Produtos");
        instance.insert("name, price", "'Notebook Azus', '2700'", "Produtos");
        instance.insert("name, price", "'Notebook Racer', '4700'", "Produtos");
        instance.insert("name, price", "'Notebook Aviall', '10700'", "Produtos");
        instance.createTable("TI", "aluno VARCHAR(255), id BIGINT");
        instance.insert("aluno, id", "'JoaoMaisLindo', '1'", "TI");
        instance.insert("aluno, id", "'TawanGostoso', '2'", "TI");
        instance.insert("aluno, id", "'MARCIOTRANSADO', '3'", "TI");
        instance.insert("aluno, id", "'ClaudionsoFudendado', '4'", "TI");
        instance.insert("aluno, id", "'luizaoElBrocador', '5'", "TI");
        instance.insert("aluno, id", "'LautoBalinhaDoce', '6'", "TI");
        instance.insert("aluno, id", "'thatizaoNarizNervoso', '7'", "TI");
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

    @DisplayName("Recuperar os Alunos de TI")
    @Test
    public void getValues(){
        assertNotNull(instance.get("id", "=", "1", "aluno", "TI"));
        assertNotNull(instance.get("id", "=", "2", "aluno", "TI"));
        assertNotNull(instance.get("id", "=", "3", "aluno", "TI"));
        assertNotNull(instance.get("id", "=", "4", "aluno", "TI"));
        assertNotNull(instance.get("id", "=", "5", "aluno", "TI"));
        assertNotNull(instance.get("id", "=", "6", "aluno", "TI"));
    }

    @DisplayName("Setar os valores dos Produtos")
    @Test
    public void setValues(){
        instance.set("name", "=", "Smartphone Xialong", "price", "9000", "Produtos");
    }
}
