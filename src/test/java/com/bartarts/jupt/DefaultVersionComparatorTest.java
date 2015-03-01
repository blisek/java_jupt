package com.bartarts.jupt;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by bartek on 01.03.15.
 */
public class DefaultVersionComparatorTest {
    private static DefaultVersionComparator comp;

    @BeforeClass
    public static void init() {
        comp = new DefaultVersionComparator();
    }

    @Test(expected = VersionFormatException.class)
    public void testCompareNumberFormatException() {
        String v1 = "1.a";
        String v2 = "1.b";
        comp.compare(v1, v2);
    }

    @Test(expected = VersionFormatException.class)
    public void testCompareNumberFormatException2() {
        String v1 = "1..0";
        String v2 = "1.0";
        comp.compare(v1, v2);
    }

    @Test
    public void testCompareSameLengthVersions() {
        String v1 = "1.0.2";
        String v2 = "1.0.5";

        assertTrue(comp.compare(v1, v2) > 0);
        assertTrue(comp.compare(v2, v1) < 0);
    }

    @Test
    public void testCompareSameLengthVersions2() {
        String v1 = "1.0", v2 = "1.0";

        assertTrue(comp.compare(v1, v2) == 0);
        assertTrue(comp.compare(v2, v1) == 0);
    }

}
