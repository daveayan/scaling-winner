package com.daveayan.scalingwinner.cache;

public class CacheItem {
    Object key = new Object();
    Object value = new Object();

    public Object getKey() {
        return this.key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    

}
