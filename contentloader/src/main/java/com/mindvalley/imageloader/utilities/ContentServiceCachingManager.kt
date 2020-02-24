package com.mindvalley.imageloader.utilities

import android.util.LruCache

import com.mindvalley.imageloader.models.ServiceContentTypeDownload

object ContentServiceCachingManager
{
    private val maxCacheSize: Int
    private val mDownloadDataTypeCache: LruCache<String, ServiceContentTypeDownload>

    init
    {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        // Use 1/8th of the available memory for this memory cache.
        maxCacheSize = maxMemory / 8 // 4 * 1024 * 1024; // 4MiB
        mDownloadDataTypeCache = LruCache(maxCacheSize)
    }

    fun getMDownloadDataType(key: String): ServiceContentTypeDownload?
    {
        return mDownloadDataTypeCache.get(key)
    }

    fun putMDownloadDataType(key: String,
                             serviceContentTypeDownload: ServiceContentTypeDownload): Boolean
    {
        return mDownloadDataTypeCache.put(key, serviceContentTypeDownload) != null
    }

    fun clearDataCache()
    {
        mDownloadDataTypeCache.evictAll()
    }

}