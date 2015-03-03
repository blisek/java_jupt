package com.bartarts.jupt;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by bartek on 03.03.15.
 */
public class HttpPostXMLProvider extends UpdateInfoProvider {

    protected URL url;
    protected List<UpdateInfo> updateInfoList;
    protected static final String requestDelimiter = "&";

    public HttpPostXMLProvider(URL address) {
        if(address == null)
            throw new NullPointerException();
        url = address;
    }

    public HttpPostXMLProvider(String address) throws MalformedURLException {
        this(new URL(address));
    }

    public HttpPostXMLProvider(URI address) throws MalformedURLException {
        this(address.toURL());
    }

    /**
     * Wczytuje liste klas z serwera, uzywajac zapytania GET z ustawionymi parametrami z UpdateInfoProvider {@see UpdateInfoProvider}.
     */
    public void reloadList() {
        for(int i = 1; i <= NUM_OF_ATTEMPTS; ++i) {
            try {
                String params = joinParameters(requestDelimiter);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Language", "en-US,en,q=0.5");
                con.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                dos.writeBytes(params);
                dos.flush();
                dos.close();
                int response = con.getResponseCode();
                if (response != 200) return;
                InputStream inputStream = con.getInputStream();
                List<UpdateInfo> l = UpdateInfoParsers.parseXML(inputStream);
                updateInfoList = l;
                inputStream.close();
                break;
            } catch (Exception e) {
                try { Thread.sleep(i * 1000); } catch (InterruptedException ie) {}
                continue;
            }
        }
    }

    @Override
    public List<UpdateInfo> getList() {
        return null;
    }
}
