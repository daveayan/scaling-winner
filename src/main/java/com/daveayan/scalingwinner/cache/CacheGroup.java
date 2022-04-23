package com.daveayan.scalingwinner.cache;

import java.util.ArrayList;
import java.util.List;

public class CacheGroup {
    String name = new String();
    List<CacheItem> cacheItems = new ArrayList<CacheItem>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CacheItem> getCacheItems() {
        return this.cacheItems;
    }

    public void setCacheItems(List<CacheItem> cacheItems) {
        this.cacheItems = cacheItems;
    }

    public void addCacheItem(CacheItem cacheItem) {
        this.cacheItems.add(cacheItem);
    }
}
