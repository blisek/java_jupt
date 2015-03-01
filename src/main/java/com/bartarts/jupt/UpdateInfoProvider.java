package com.bartarts.jupt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by bartek on 01.03.15.
 */
public abstract class UpdateInfoProvider {

    public abstract List<UpdateInfo> getList();

    public final Map<String, UpdateInfo> getMap() {
        List<UpdateInfo> list = getList();
//        Map<String, UpdateInfo> map = new HashMap<>(list.size());
//        for(UpdateInfo ui : list)
//            map.put(ui.className, ui);
//        return map;
        return list.stream().collect(Collectors.toMap(updateInfo -> updateInfo.className, updateInfo -> updateInfo));
    }

}
