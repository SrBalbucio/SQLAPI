package balbucio.sqlapi.h2;

import balbucio.sqlapi.common.SQLConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class H2Config extends SQLConfig {

    private File databaseFile;
    private String user = "sa";
    private String password = "password";

    public H2Config(File databaseFile) {
        this.databaseFile = databaseFile;
    }

    public void createFile(){
        try{
            this.databaseFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void recreateFile(){
        try{
            this.databaseFile.delete();
            this.databaseFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
