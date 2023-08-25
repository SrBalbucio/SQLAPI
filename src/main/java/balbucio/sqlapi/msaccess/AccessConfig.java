package balbucio.sqlapi.msaccess;

import balbucio.sqlapi.common.SQLConfig;
import lombok.Data;

import java.io.File;

@Data
public class AccessConfig extends SQLConfig {

    private boolean memory;
    private File file;

    public AccessConfig(File file) {
        this.file = file;
    }

    public AccessConfig(boolean memory, File file) {
        this.memory = memory;
        this.file = file;
    }
}
