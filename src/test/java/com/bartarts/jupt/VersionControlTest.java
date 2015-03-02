package com.bartarts.jupt;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by bartek on 01.03.15.
 */
public class VersionControlTest {
    private VersionControl vc;
    private static Class<?>[] expectedClasses = { TestClass1.class, TestClass3.class };

    @Test
    public void testConstructFromClassList() {
        vc = new VersionControl(new UpdateInfoProviderMock(), Arrays.asList(TestClass1.class, TestClass2.class, TestClass3.class));
        Class<?>[] classes = vc.getClassesUnderVersionControl().toArray(new Class<?>[expectedClasses.length]);
        assertEquals(2, classes.length);
        for(Class c : classes)
            System.out.println(c.getCanonicalName());
        Arrays.sort(classes, new Comparator<Class<?>>() {
            @Override
            public int compare(Class<?> o1, Class<?> o2) {
                return o1.getCanonicalName().compareTo(o2.getCanonicalName());
            }
        });
        assertArrayEquals(expectedClasses, classes);
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
        assertEquals(2, classes.length);
        assertArrayEquals(expectedClasses, classes);
    }

    @Test
    public void testListAllNewerClasses() {
        UpdateInfo[] updateInfos = {
                new UpdateInfoMock(TestClass1.class.getCanonicalName(), "", "1.0.0", ""),
                new UpdateInfoMock(TestClass3.class.getCanonicalName(), "", "1.6", "")
        };

        UpdateInfo[] expected = { updateInfos[1] };
        vc = new VersionControl(new UpdateInfoProviderMock(updateInfos), Arrays.asList(TestClass1.class, TestClass2.class, TestClass3.class));
        UpdateInfo[] recieved = vc.listAllNewerClasses().toArray(new UpdateInfo[1]);

        assertTrue(recieved.length == 1);
        assertEquals(expected[0].className, recieved[0].className);
    }

    @Test
    public void testListAllAvailableClasses() {
        UpdateInfo[] updateInfos = {
                new UpdateInfoMock(TestClass1.class.getCanonicalName(), "", "1.0.0", ""),
                new UpdateInfoMock(TestClass3.class.getCanonicalName(), "", "1.6", "")
        };
        Set<String> classes = new HashSet<>();
        Arrays.stream(updateInfos).forEach(ui -> classes.add(ui.className));
        vc = new VersionControl(new UpdateInfoProviderMock(updateInfos), Arrays.asList(TestClass1.class, TestClass2.class, TestClass3.class));
        vc.listAllAvailableClasses().stream().forEach(ui -> classes.remove(ui.className));
        assertTrue(classes.size() == 0);
    }

}

class UpdateInfoProviderMock extends UpdateInfoProvider {
    private List<UpdateInfo> updateInfoList;

    public UpdateInfoProviderMock() {
    }

    public UpdateInfoProviderMock(UpdateInfo... updateInfos) {
        updateInfoList = new ArrayList<>(Arrays.<UpdateInfo>asList(updateInfos));
    }

    @Override
    public List<UpdateInfo> getList() {
        return updateInfoList;
    }
}

class UpdateInfoMock extends UpdateInfo {

    public UpdateInfoMock(String className, String classPackage, String newVersion, String newVersionDescription) {
        super(className, classPackage, newVersion, newVersionDescription);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }
}