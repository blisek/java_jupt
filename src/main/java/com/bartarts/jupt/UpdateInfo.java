package com.bartarts.jupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Struktura zawierajaca informacje o wersji pliku na serwerze.
 */
public abstract class UpdateInfo {
    /**
     * Pelna nazwa klasy (z nazwa pakietu)
     */
    String className;
    /**
     * Nazwa pakietu
     */
    String classPackage;
    /**
     * Numer nowej wersji np. 1.0.0.1
     */
    String newVersion;
    /**
     * Opis nowej wersji
     */
    String newVersionDescription;

    public UpdateInfo(String className, String classPackage, String newVersion, String newVersionDescription) {
        this.className = className;
        this.classPackage = classPackage;
        this.newVersion = newVersion;
        this.newVersionDescription = newVersionDescription;
    }

    public String getClassName() {
        return className;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public String getNewVersionDescription() {
        return newVersionDescription;
    }

    public abstract InputStream getInputStream() throws IOException;
}
