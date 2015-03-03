package com.bartarts.jupt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by bartek on 01.03.15.
 */
public abstract class UpdateInfoProvider {

    public static int NUM_OF_ATTEMPTS = 5;

    private Map<String, String> requestBodyParameters = new HashMap<>();

    public abstract List<UpdateInfo> getList();

    /**
     * Returns Map where each key is name of class (including package name) and UpdateInfo struct as corresponding value.
     * @return above described Map.
     */
    public final Map<String, UpdateInfo> getMap() {
        List<UpdateInfo> list = getList();
        return list.stream().collect(Collectors.toMap(updateInfo -> updateInfo.className, updateInfo -> updateInfo));
    }

    /**
     * Returns mutable Map of request body parameters. Example: http://www.site.com/index.php?key1=value1&key2=value2,
     * where pairs (key1, value1) and (key2, value2) are from requestBodyParameters Map.
     * @return Map
     */
    public Map<String, String> requestBodyParametersMap() {
        return requestBodyParameters;
    }

    protected String joinParameters(String delimiter) {
        if(requestBodyParameters.isEmpty())
            return new String();
        StringBuilder sb = new StringBuilder(requestBodyParameters.size());
        final boolean[] firstParameter = {true};
        requestBodyParameters.forEach((key,val) -> {
            if(firstParameter[0]) firstParameter[0] = false;
            else sb.append(delimiter);
            sb.append(String.join("=", key, val));
        });
        return sb.toString();
    }

}
