package com.bartarts.jupt;

import java.io.File;
import java.util.Collection;

/**
 * Created by bartek on 01.03.15.
 */
public class Updater {
    private Collection<? super UpdateInfo> listOfClasses;
    private File workingDir;

    public Updater(File workingDir, Collection<? super UpdateInfo> updateInfoList) {
        if(workingDir == null)
            throw new NullPointerException("workingDir");
        if(updateInfoList == null)
            throw new NullPointerException("updateInfoList");
        if(!workingDir.isDirectory())
            throw new IllegalArgumentException("workingDir isn't directory");
        this.workingDir = workingDir;
        listOfClasses = updateInfoList;
    }

    public Updater(Collection<? super UpdateInfo> updateInfoList) {
        this(new File(System.getProperty("user.dir")), updateInfoList);
    }

    /**
     * Tworzy potrzebne katalogi i zapisuje w nich nowsze wersje klas z danego zrodla.
     * Jezeli w strukturze katalogow istnieje juz taka klasa jest podmieniana na nowa
     * przy konczeniu dzialania.
     * @return true jesli wszystko po zakonczeniu dzialania metody zostalo skopiowane
     * do odpowiednich miejsc.
     */
    public boolean performUpdate() {
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