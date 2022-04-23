package com.daveayan.scalingwinner.cache;

import java.util.ArrayList;
import java.util.List;

public class CacheGroups {
    List<CacheGroup> cacheGroups = new ArrayList<CacheGroup>();


    public List<CacheGroup> getCacheGroups() {
        return this.cacheGroups;
    }

    public void setCacheGroups(List<CacheGroup> cacheGroups) {
        this.cacheGroups = cacheGroups;
    }

    public void addCacheGroup(CacheGroup cacheGroup) {
        this.cacheGroups.add(cacheGroup);
    }
}
