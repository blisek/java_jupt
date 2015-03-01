package com.bartarts.jupt;

import java.util.Comparator;

/**
 * Adnotacja dla aktualizatora.
 * Klasy nieoznaczone tą adnotacją nie mogą podlegać kontroli wersji.
 */
public @interface VersionControlInfo {
    /**
     * Lancuch znakow oznaczajacy wersje
     */
    String version();

    /**
     * Comparator do porownywania wersji. Przekazana klasa powinna implementowac Comparator[String].
     * Dodatkowo klasa musi posiadac bezargumentowy konstruktor.
     */
    Class<? extends Comparator<String>> versionComparator() default DefaultVersionComparator.class;
}
