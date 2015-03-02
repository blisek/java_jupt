package com.bartarts.jupt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bartek on 02.03.15.
 */
public class UpdateInfoFile extends UpdateInfo {
    public static int NUM_OF_ATTEMPTS = 3;
    protected File file;

    public UpdateInfoFile(String className, String classPackage, String newVersion, String newVersionDescription, File file) {
        super(className, classPackage, newVersion, newVersionDescription);
        if(file == null)
            throw new NullPointerException("file");
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = null;
        for(int i = 1; i <= NUM_OF_ATTEMPTS; ++i) {
            try {
                is = new FileInputStream(file);
                break;
            } catch (IOException e) {
                if(i < NUM_OF_ATTEMPTS)
                    continue;
                else
                    throw e;
            }
        }
        return is;
    }
}
