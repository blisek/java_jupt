package com.bartarts.jupt;

/**
 * Created by bartek on 01.03.15.
 */
public class Updater {
    private final String packageName;

    public Updater(String packageName) {
        if(packageName == null)
            throw new NullPointerException();

        this.packageName = packageName;
    }
}
