package com.daveayan.scalingwinner.cache;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cache")
public class CacheController {
    private static final Logger LOG = LoggerFactory.getLogger(CacheController.class);

    @Autowired CacheManager cacheManager;

    @GetMapping("")
    ResponseEntity<CacheGroups> getAllCacheEntries() {
        LOG.trace("IN getAllCacheEntries");
        CacheGroups cacheGroups = buildCacheGroups();
        LOG.trace("OUT getAllCacheEntries");
        return new ResponseEntity<CacheGroups>(cacheGroups, HttpStatus.OK);
    }

    @GetMapping("/{cacheName}/{key}")
    ResponseEntity<CacheItem> getCacheByKey(@PathVariable String cacheName, @PathVariable Long key) {
        LOG.trace("IN getCacheByKey");
        
        CacheItem cacheItem = buildCacheItem(cacheName, key);

        LOG.trace("OUT getCacheByKey");
        return new ResponseEntity<CacheItem>(cacheItem, HttpStatus.OK);
    }

    @DeleteMapping("/{cacheName}")
    ResponseEntity<CacheGroups> invalidateCache(@PathVariable String cacheName) {
        LOG.trace("IN invalidateCache");

        Cache cacheItems = cacheManager.getCache(cacheName);
        if(cacheItems != null) {
            cacheItems.invalidate();
        }

        CacheGroups cacheGroups = buildCacheGroups();

        LOG.trace("OUT invalidateCache");
        return new ResponseEntity<CacheGroups>(cacheGroups, HttpStatus.OK);
    }
    
    @DeleteMapping("/{cacheName}/{key}")
    ResponseEntity<CacheGroups> evictEntry(@PathVariable String cacheName, @PathVariable Long key) {
        LOG.trace("IN evictEntry");

        Cache cacheItems = cacheManager.getCache(cacheName);
        System.out.println(cacheItems);
        if(cacheItems != null) {
            cacheItems.evict(key);
        }

        CacheGroups cacheGroups = buildCacheGroups();

        LOG.trace("OUT evictEntry");
        return new ResponseEntity<CacheGroups>(cacheGroups, HttpStatus.OK);
    }

    private CacheGroups buildCacheGroups () {
        CacheGroups cacheGroups = new CacheGroups();

        Collection<String> cacheNames = cacheManager.getCacheNames();

        Object[] arrCacheNames = (Object []) cacheNames.toArray();

        for(int i = 0; i < arrCacheNames.length; i++) {
            CacheGroup cacheGroup = new CacheGroup();
            
            cacheGroup.setName(arrCacheNames[i].toString());
            Cache thisCache = cacheManager.getCache(arrCacheNames[i].toString());
            
            Map<Object, Object> nativeCache = (Map<Object, Object>) thisCache.getNativeCache();
            Object[] keySet = (Object[]) nativeCache.keySet().toArray();

            for (int j = 0; j < keySet.length; j++) {
                Object key = keySet[j];
                String value = nativeCache.get(key).toString();
                CacheItem cacheItem = new CacheItem();
                cacheItem.setKey(key);
                cacheItem.setValue(value);
                cacheGroup.addCacheItem(cacheItem);
            }

            cacheGroups.addCacheGroup(cacheGroup);
        }
        return cacheGroups;
    }

    private CacheItem buildCacheItem (String cacheName, Long key) {
        Collection<String> cacheNames = cacheManager.getCacheNames();

        Object[] arrCacheNames = (Object []) cacheNames.toArray();

        for(int i = 0; i < arrCacheNames.length; i++) {
            if(cacheName.trim().equalsIgnoreCase(arrCacheNames[i].toString().trim())) {
                Cache thisCache = cacheManager.getCache(arrCacheNames[i].toString());
            
                Map<Object, Object> nativeCache = (Map<Object, Object>) thisCache.getNativeCache();
                Object[] keySet = (Object[]) nativeCache.keySet().toArray();
    
                for (int j = 0; j < keySet.length; j++) {
                    Object cacheKey = keySet[j];
                    if(cacheKey.equals(key)) {
                        String value = nativeCache.get(key).toString();
                        CacheItem cacheItem = new CacheItem();
                        cacheItem.setKey(key);
                        cacheItem.setValue(value);
    
                        return cacheItem;
                    }
                }
            }
        }
        CacheItem cacheItem = new CacheItem();
        cacheItem.setKey("N.A.");
        cacheItem.setValue("N.A.");
        return cacheItem;
    }
}
