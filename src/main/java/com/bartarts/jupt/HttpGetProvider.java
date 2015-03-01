package com.bartarts.jupt;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by bartek on 01.03.15.
 */
public class HttpGetProvider extends UpdateInfoProvider {
    protected URL url;
    protected List<UpdateInfo> updateInfoList;

    public HttpGetProvider(URL address) {
        if(address == null)
            throw new NullPointerException();
        url = address;
    }

    public HttpGetProvider(String address) throws MalformedURLException {
        this(new URL(address));
    }

    public HttpGetProvider(URI address) throws MalformedURLException {
        this(address.toURL());
    }

    public void reloadList() {

    }

    @Override
    public List<UpdateInfo> getList() {
        if(updateInfoList != null)
            return updateInfoList;
        reloadList();
        return updateInfoList;
    }
}
