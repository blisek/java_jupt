package com.bartarts.jupt;

import java.util.Comparator;

/**
 * Domyslna implementacja porownujaca numery wersji.
 */
public class DefaultVersionComparator implements Comparator<String> {
    private static final String regex = "\\.";
    /**
     * * Domyslna implementacja porownujaca wersje postaci n.n.n..., gdzie n to dowolna liczba calkowita
     * a kropki oddzielaja poszczegolne liczby (ciag 'n' i '.' moze miec dowolna dlugosc).
     * @param o1 pierwszy, porownywany, numer wersji
     * @param o2 drugi, porownywany, numer wersji
     * @return 0 jesli porownywane wersje sa takie same, 1 jesli drugi numer jest wiekszy, -1 w przeciwnym wypadku
     * @throws VersionFormatException rzucany, gdy 'n' nie jest liczba lub przekazano niepoprawny format wersji
     */
    @Override
    public int compare(String o1, String o2) throws VersionFormatException {
        String[] versionString1 = o1.split(regex);
        if(hasEmptyStrings(versionString1))
            throw new VersionFormatException("Incorrect version: " + o1);
        String[] versionString2 = o2.split(regex);
        if(hasEmptyStrings(versionString2))
            throw new VersionFormatException("Incorrect version: " + o2);

        if(versionString1.length < versionString2.length)
            return 1;
        else if(versionString1.length > versionString2.length)
            return -1;

        try {
            for (int i = 0; i < versionString1.length; ++i) {
                int num1 = Integer.parseInt(versionString1[i]);
                int num2 = Integer.parseInt(versionString2[i]);

                if (num1 < num2) return 1;
                else if (num1 > num2) return -1;
            }
        } catch(NumberFormatException ex) {
            throw new VersionFormatException(ex.getMessage());
        }
        return 0;
    }

    private static boolean hasEmptyStrings(String[] array) {
        for(String s : array) {
            if(s.equals(""))
                return true;
        }
        return false;
    }
}
