package balbucio.sqlapi.common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ISQL {

    public abstract void connect();
    public abstract Statement getStatement() throws SQLException;
    public abstract Connection getConnection();

    /**
     * Checa se a conexão atual ainda existe
     * @return
     */
    public boolean isConnected(){
        try {
            return getConnection() != null && getConnection().isValid(100);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cria uma tabela no banco de dados
     * @param tableName Nome da Tabela (Ex.: times)
     * @param columns Colunas da Tabela (Ex.: jogador VARCHAR(255), id BIGINT)
     */
    public void createTable(String tableName, String columns){
        try {
            if (!isConnected()) {
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+tableName+" ("+columns+");");
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Verifica se uma tabela ja existe
     * @param table nome da tabela
     * @return
     */
    public boolean tableExists(String table){
        boolean exists = false;
        try {
            if (!isConnected()) {
                connect();
            }

            DatabaseMetaData data = getConnection().getMetaData();
            ResultSet set = data.getTables(null, null, table, null);
            if(set == null)
                exists = false;
            if(set.next())
                exists = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Retorna o nome de todas as tabelas
     * @return
     */
    public List<String> getTableNames(){
        List<String> tables = new ArrayList<>();
        try{
            if(!isConnected()){
                connect();
            }

            DatabaseMetaData data =  getConnection().getMetaData();
            ResultSet set = data.getTables(null, null, "%", null);
            while(set.next()){
                tables.add(set.getString(3));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return tables;
    }

    public void truncateTable(String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("TRUNCATE TABLE "+tableName+";");
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Insere dados a uma tabela
     * @param columns Colunas que você deseja preencher (Ex.: jogador, id)
     * @param values Valores que você deseja adicionar (Ex.: 'Neymar', '1')
     * @param tableName Tabela que deve ser adicionado
     */
    public void insert(String columns, String values, String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("INSERT INTO "+tableName+" ("+columns+") VALUES ("+values+");");
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Insere novos dados pra substituir o antigo
     * @param columns Colunas que você deseja preencher (Ex.: jogador, id)
     * @param values Valores que você deseja adicionar (Ex.: 'Neymar', '1')
     * @param tableName Tabela que deve ser adicionado
     */
    public void replace(String columns, String values, String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("INSERT IGNORE INTO "+tableName+" ("+columns+") VALUES ("+values+");");
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Seta os dados
     * @param columnSelected Coluna que você usará para comparar (Ex.: jogador)
     * @param logic (Ex.: = )
     * @param data Dado para ser comparado (Ex.: "neymar")
     * @param selected Dado a ser alterado
     * @param newValue Novo valor
     * @param tableName Tabela
     */
    public void set(String columnSelected, String logic, String data, String selected,  Object newValue, String tableName){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate("UPDATE "+tableName+" SET "+selected+" = "+newValue+" WHERE "+columnSelected+" "+logic+" "+data+";");
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Retorna um dado
     * @param columnSelected Coluna que você usará para comparar (Ex.: jogador)
     * @param logic (Ex.: = )
     * @param data Dado para ser comparado (Ex.: "neymar")
     * @param selected Coluna onde o dado que deseja está
     * @param tableName Tabela
     * @return
     */
    public Object get(String columnSelected, String logic, String data, String selected, String tableName){
        Object obj = null;
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            ResultSet set = statement.executeQuery("SELECT "+selected+" FROM "+tableName+" WHERE "+columnSelected+" "+logic+" "+data+";");
            while (set.next()){
                obj = set.getObject(selected);
            }
            statement.close();
        } catch (Exception e){
            e.printStackTrace();

        }
        return obj;
    }

    /**
     * Retorna todos os dados que encaixem na comparação
     * @param columnSelected Coluna que você usará para comparar (Ex.: jogador)
     * @param logic (Ex.: = )
     * @param data Dado para ser comparado (Ex.: "neymar")
     * @param selected Coluna onde o dado que deseja está
     * @param tableName Tabela
     * @return
     */
    public List<Object> getAll(String columnSelected, String logic, String data, String selected, String tableName){
        List<Object> objects = new ArrayList<>();
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            ResultSet set = statement.executeQuery("SELECT "+selected+" FROM "+tableName+" WHERE "+columnSelected+" "+logic+" "+data+";");
            while(set.next()){
                objects.add(set.getObject(selected));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return objects;
    }

    /**
     * Retorna o valor de todas as colunas solicitadas
     * @param tableName Tabela
     * @param columns Ex.: jogador, id
     * @return
     */
    public List<Object[]> getAllValuesFromColumns(String tableName, String... columns){
        List<Object[]> objs = new ArrayList<>();
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM "+tableName);
            while(set.next()){
                Object[] values = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    values[i] = set.getObject(columns[i]);
                }
                objs.add(values);
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return objs;
    }

    /**
     * Executa uma query
     */
    public ResultSet query(String query){
        ResultSet set = null;
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            set = statement.executeQuery(query);
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Executa um update
     * @param query
     */
    public void update(String query){
        try{
            if(!isConnected()){
                connect();
            }

            Statement statement = getStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
