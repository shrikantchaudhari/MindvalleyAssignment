package com.mindvalley.asyncloader.utilities;

import android.util.LruCache;

import com.mindvalley.asyncloader.models.CommonRequestModel;


public class CacheManager {

    private static CacheManager instance;

    private LruCache<String, CommonRequestModel> mDownloadDataTypeCache;

    private CacheManager() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        int maxCacheSize = maxMemory / 8;

        mDownloadDataTypeCache = new LruCache<>(maxCacheSize);
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    /**
     * This function will clear the cache
     */
    public void clearCache() {
        mDownloadDataTypeCache.evictAll();
    }

    /**
     * This function will return the data type for request which is processing
     *
     * @param key : Key for identifying data
     * @return : CommonRequestModel
     */
    public CommonRequestModel getMDownloadDataType(String key) {
        return mDownloadDataTypeCache.get(key);
    }

    /**
     * This function will put the data type for the request
     *
     * @param key                        : String key
     * @param commonRequestModel : CommonRequestModel
     * @return : Boolean
     */
    public boolean putMDownloadDataType(String key, CommonRequestModel commonRequestModel) {
        return mDownloadDataTypeCache.put(key, commonRequestModel) != null;
    }


}
