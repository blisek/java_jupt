package com.bartarts.jupt;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bartek on 01.03.15.
 */
public class VersionControlTest {
    private VersionControl vc;
    private static Class<?>[] expectedClasses = { TestClass1.class, TestClass3.class };

    @Test
    public void testConstructFromClassList() {
        vc = new VersionControl(new UpdateInfoProviderMock(), Arrays.asList(TestClass1.class, TestClass2.class, TestClass3.class));

        assertArrayEquals(expectedClasses, vc.getClassesUnderVersionControl().toArray(new Class<?>[expectedClasses.length]));
    }

    @Test
    public void testConstructFromPackageName() {
        vc = new VersionControl(new UpdateInfoProviderMock(), "com.bartarts.jupt");
        Class<?>[] classes = vc.getClassesUnderVersionControl().toArray(new Class<?>[expectedClasses.length]);
        Arrays.sort(classes, new Comparator<Class<?>>() {
            @Override
            public int compare(Class<?> o1, Class<?> o2) {
                return o1.getCanonicalName().compareTo(o2.getCanonicalName());
            }
        });
        assertArrayEquals(expectedClasses, classes);
    }

}

class UpdateInfoProviderMock extends UpdateInfoProvider {
    @Override
    public List<UpdateInfo> getList() {
        return null;
    }
}