package com.bartarts.jupt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by bartek on 02.03.15.
 */
public class UpdateInfoURL extends UpdateInfo {
    public static int NUM_OF_ATTEMPTS = 5;
    protected URL link;

    public UpdateInfoURL(String className, String classPackage, String newVersion, String newVersionDescription, URL link) {
        super(className, classPackage, newVersion, newVersionDescription);
        if(link == null)
            throw new NullPointerException();
        this.link = link;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = null;
        for(int i = 1; i <= NUM_OF_ATTEMPTS; ++i) {
            try {
                is = link.openStream();
                break;
            } catch (IOException e) {
                if(i < NUM_OF_ATTEMPTS)
                    continue;
                else
                    throw e;
            }
        }
        return is;
    }
}
