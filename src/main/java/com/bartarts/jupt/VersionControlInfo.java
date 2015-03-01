package com.bartarts.jupt;

import java.util.Comparator;

/**
 * Adnotacja dla aktualizatora.
 * Klasy nieoznaczone tą adnotacją nie mogą podlegać kontroli wersji.
 */
public @interface VersionControlInfo {
    String currentVersion();
    Class<? extends Comparator<String>> versionComparator() default DefaultVersionComparator.class;
}
