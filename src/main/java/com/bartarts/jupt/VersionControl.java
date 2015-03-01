package com.bartarts.jupt;

import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by bartek on 01.03.15.
 */
public class VersionControl {
    private Set<Class<?>> classes;
    private UpdateInfoProvider provider;

    public VersionControl(UpdateInfoProvider provider, List<Class<?>> classList) {
        if(provider == null || classList == null)
            throw new NullPointerException();

        classes = classList.stream()
                .filter(c -> c.isAnnotationPresent(VersionControlInfo.class))
                .collect(Collectors.toSet());
        this.provider = provider;
    }

    public VersionControl(UpdateInfoProvider provider, String packageName) {
        Reflections reflections = new Reflections(packageName);
        classes = reflections.getTypesAnnotatedWith(VersionControlInfo.class);
        this.provider = provider;
    }

    /**
     * Zwraca liste wszystkich dostepnych klas wg danego providera. Metoda moze wstrzymac
     * na dlugo dzialanie programu, jesli lista pobierana jest z zewnetrznego serwera.
     * @return lista wszystkich dostepnych klas
     */
    public List<UpdateInfo> listAllAvailableClasses() {
        return provider.getList();
    }

    /**
     * Zwraca liste wszystkich klas o nowszej wersji niz obecna.Metoda moze wstrzymac
     * na dlugo dzialanie programu, jesli lista pobierana jest z zewnetrznego serwera.
     * @return lista klas mozliwych do zaktualizowania
     */
    public List<UpdateInfo> listAllNewerClasses() {
        Map<String, UpdateInfo> mapOfUpdateInfo = provider.getMap();
        List<UpdateInfo> list = new ArrayList<>(mapOfUpdateInfo.size());

        for(Class<?> cl : classes) {
            VersionControlInfo v = cl.getAnnotation(VersionControlInfo.class);
            UpdateInfo ui = mapOfUpdateInfo.get(cl.getCanonicalName());
            if(ui == null) continue;
            Comparator<String> comp;
            try {
                comp = v.versionComparator().newInstance();
            } catch (Exception ex) {
                //TODO: wpis do logow
                continue;
            }
            if(comp.compare(v.version(), ui.newVersion) > 0)
                list.add(ui);
        }

        return list;
    }

    /**
     * Zwraca Liste klas, ktorych wersja bedzie sprawdzana.
     * @return lista klas obslugiwanych przez kontrole wersji.
     */
    public List<Class<?>> getClassesUnderVersionControl() {
        return classes.stream().collect(Collectors.toList());
    }
}
