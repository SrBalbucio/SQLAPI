package balbucio.sqlapi;

import balbucio.sqlapi.h2.H2Config;
import balbucio.sqlapi.h2.H2Instance;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class H2TEST {

    H2Config config = new H2Config(new File("database-h2.db"));
    H2Instance instance;
    int i = 1;

    @BeforeAll
    public void init(){
        config.recreateFile();
        instance = new H2Instance(config);
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
        System.out.println(instance.tableExists("MEDIO"));
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
    public void getValue(){
        assertNotEquals(null, instance.get("id", "=", String.valueOf(i), "aluno", "TI"));
    }


    @DisplayName("Recuperando as informações de tabela")
    @Test
    public void getTables(){
        assertTrue(!instance.getTableNames().isEmpty(), "Verificando se as tabelas retornaram");
    }

    @DisplayName("Recuperando as informações da coluna")
    @Test
    public void getColumns(){
        assertFalse(instance.getColumns("grupos").isEmpty());
    }

    @DisplayName("Checar se tabela existe")
    @Test
    public void tableExists(){
        assertTrue(instance.tableExists("Produtos"));
    }

    @DisplayName("Deletar item")
    @Test
    public void delete(){
        instance.delete("id", 1, "grupos");
        assertNull(instance.get("id", "=", "1", "`group-name`", "grupos"));
    }

    @DisplayName("Checar se não existe")
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

    @DisplayName("Outro check")
    @Test
    public void otherExists(){
        instance.insert("aluno, id", "'MARCIOTRANSADO', '3'", "INGLES");
        assertTrue(instance.exists("id", "=", "3", "INGLES"));
    }

    @DisplayName("Set com multiplas Conditions")
    @Test
    public void setCondition(){
        instance.createTable("testeSet", "id BIGINT, name VARCHAR(255), type VARCHAR(255)");
        instance.insert("id, name, type", "'1', 'a', 's'", "testeSet");
        instance.set(new ConditionValue[] {
                new ConditionValue("id", Conditional.EQUALS, 1, Operator.NULL),
                new ConditionValue("name", Conditional.EQUALS, "a", Operator.AND)
        }, "type", "abc", "testeSet");
        assertEquals("abc", instance.get("id", "=", "1", "type", "testeSet"));
    }

    @DisplayName("Teste de retorno com ResultValue")
    @Test
    public void resultValue(){
        List<ResultValue> value = instance.getAllValuesFromColumns("TI");
        assertFalse(value.isEmpty());
    }

    @DisplayName("Teste de retorno do Order")
    @Test
    public void resultOrder(){
        List<ResultValue> value = instance.getAllValuesOrderedBy("price", "Produtos");
        assertFalse(value.isEmpty());
    }

}
