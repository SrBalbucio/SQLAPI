package balbucio.sqlapi.sqlite;

import balbucio.sqlapi.common.SQLConfig;

import java.io.File;

public class SqliteConfig extends SQLConfig {

    private String id = "default";
    private boolean memory;
    private File databaseFile;

    public SqliteConfig(boolean memory){
        this.memory = memory;
    }

    public SqliteConfig(File databaseFile){
        this.databaseFile = databaseFile;
        this.memory = false;
    }

    public SqliteConfig(boolean memory, File databaseFile) {
        this.memory = memory;
        this.databaseFile = databaseFile;
    }

    public SqliteConfig(String id, boolean memory, File databaseFile) {
        this.id = id;
        this.memory = memory;
        this.databaseFile = databaseFile;
    }

    public void createFile(){
        try{
            this.databaseFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMemory() {
        return memory;
    }

    public void setMemory(boolean memory) {
        this.memory = memory;
    }

    public File getDatabaseFile() {
        return databaseFile;
    }

    public void setDatabaseFile(File databaseFile) {
        this.databaseFile = databaseFile;
    }
}
