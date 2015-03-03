package com.bartarts.jupt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by bartek on 01.03.15.
 */
public class Updater {
    private Collection<UpdateInfo> listOfClasses;
    private File workingDir;

    public Updater(File workingDir, Collection<UpdateInfo> updateInfoList) {
        if(workingDir == null)
            throw new NullPointerException("workingDir");
        if(updateInfoList == null)
            throw new NullPointerException("updateInfoList");
        if(!workingDir.isDirectory())
            throw new IllegalArgumentException("workingDir isn't directory");
        this.workingDir = workingDir;
        listOfClasses = updateInfoList;
    }

    public Updater(Collection<UpdateInfo> updateInfoList) {
        this(new File(System.getProperty("user.dir")), updateInfoList);
    }

    /**
     * Tworzy potrzebne katalogi i zapisuje w nich nowsze wersje klas z danego zrodla.
     * Jezeli w strukturze katalogow istnieje juz taka klasa jest podmieniana na nowa
     * przy konczeniu dzialania.
     * @param registerHooks niezakonczone dzialania zostana odpalone przy wylaczaniu programu.
     * @param incompleteTasks opcjonalny parametr, w liscie zostana umiejscowione nieukonczone zadania.
     *                        Jesli registerHooks jest true, zadania umieszczone na tej liscie zostana
     *                        dodane do zadan uruchamianych przy wylaczaniu maszyny wirtualnej.
     * @param failureTasks opcjonalny parametr, lista plikow ktorych nie udalo sie zaktualizowac.
     * @return true jesli wszystko po zakonczeniu dzialania metody zostalo skopiowane
     * do odpowiednich miejsc.
     */
    public boolean performUpdate(boolean registerHooks, Optional<List<Thread>> incompleteTasks, Optional<List<UpdateInfo>> failureTasks) {
        List<Thread> thList =
                (incompleteTasks == null || !incompleteTasks.isPresent()) ? new ArrayList<>() : incompleteTasks.get();
        List<UpdateInfo> failureList =
                (failureTasks == null || !failureTasks.isPresent()) ? new ArrayList<>() : failureTasks.get();

        for(UpdateInfo ui : listOfClasses) {
            try {
                String packagePath = ui.classPackage.replace('.', '/');
                File packageDir = new File(workingDir, packagePath);
                packageDir.mkdirs();
                // TODO pobieranie
            } catch (Exception ex) {
                failureList.add(ui);
            }

        }

        return false;
    }

    private Thread wrapUpdateInfoToThread(final UpdateInfo updateInfo) {
        if(updateInfo == null)
            throw new NullPointerException();
        return new Thread(() -> {

        });
    }
}

/* Na pozniej
File workingDir = new File(System.getProperty("user.dir"));
		String[] cat = { "a/b", "b/a", "a/c", "c/a", "b/c", "c/b" };
		String[] files = { "file1.txt", "file2.txt" };
		for(String path : cat) {
			File subdirs = new File(workingDir, path);
			subdirs.mkdirs();
			for(String f : files) {
				File fi = new File(subdirs, f);
				try {
					fi.createNewFile();
				} catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
 */