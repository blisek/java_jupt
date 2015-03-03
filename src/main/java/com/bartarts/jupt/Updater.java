package com.bartarts.jupt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
                String classFile = ui.className.substring(ui.className.lastIndexOf('.')) + ".class";
                File classFileFile = new File(packageDir, classFile);
                if(classFileFile.exists()) {
                    Thread thread = wrapUpdateInfoToThread(ui, classFileFile);
                    thList.add(thread);
                    if(registerHooks)
                        Runtime.getRuntime().addShutdownHook(thread);
                } else {
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(classFileFile);
                        ReadableByteChannel rbc = Channels.newChannel(ui.getInputStream());
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    } finally {
                        if(fos != null)
                            fos.close();
                    }
                }
                // TODO pobieranie
            } catch (Exception ex) {
                failureList.add(ui);
            }

        }

        return false;
    }

    private Thread wrapUpdateInfoToThread(final UpdateInfo updateInfo, final File destinationFile) throws IOException, SecurityException{
        if(updateInfo == null || destinationFile == null)
            throw new NullPointerException();
        final File tempFile = File.createTempFile(updateInfo.className, ".tmp");
        ReadableByteChannel rbc = Channels.newChannel(updateInfo.getInputStream());
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        return new Thread(() -> {
            //FileOutputStream fos = null;
            try {
                if (destinationFile.exists()) destinationFile.delete();
                destinationFile.createNewFile();
                ReadableByteChannel byteChannel = Channels.newChannel(new FileInputStream(tempFile));
                FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
                fileOutputStream.getChannel().transferFrom(byteChannel, 0, tempFile.length() + 1);
                fileOutputStream.close();
                byteChannel.close();
                tempFile.delete();
            } catch (Exception ex) {

            }
        });
    }
}
