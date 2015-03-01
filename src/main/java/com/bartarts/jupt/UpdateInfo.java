package com.bartarts.jupt;

import java.net.URI;

/**
 * Struktura zawierajaca informacje o wersji pliku na serwerze.
 */
public class UpdateInfo {
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
    /**
     * Sciezka do pliku z nowa wersja
     */
    URI link;

    public UpdateInfo(String className, String classPackage, String newVersion, String newVersionDescription, URI link) {
        this.className = className;
        this.classPackage = classPackage;
        this.newVersion = newVersion;
        this.newVersionDescription = newVersionDescription;
        this.link = link;
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

    public URI getLink() {
        return link;
    }
}
