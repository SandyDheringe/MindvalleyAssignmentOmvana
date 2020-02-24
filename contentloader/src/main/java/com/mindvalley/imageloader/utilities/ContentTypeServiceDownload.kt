package com.mindvalley.imageloader.utilities

import android.util.Log
import com.loopj.android.http.AsyncHttpClient
import com.mindvalley.imageloader.callback.ContentServiceObserver
import com.mindvalley.imageloader.callback.ContentServiceStatusObserver
import com.mindvalley.imageloader.models.ServiceContentTypeDownload
import java.util.*

object ContentTypeServiceDownload
{
    private val allRequestsByKey = HashMap<String, LinkedList<ServiceContentTypeDownload>>()
    private val allRequestsClient = HashMap<String, AsyncHttpClient>()

    val isQueueEmpty: Boolean
        get() = allRequestsByKey.size == 0

    fun getRequest(serviceContentTypeDownload: ServiceContentTypeDownload)
    {
        val mKey = serviceContentTypeDownload.keyMD5
        // Check if exist in the cache
        val serviceContentTypeDownloadFromCache =
            ContentServiceCachingManager.getMDownloadDataType(mKey)
        if(serviceContentTypeDownloadFromCache != null)
        {
            serviceContentTypeDownloadFromCache.comeFrom = "Cache"
            // call interface
            val contentServiceObserver = serviceContentTypeDownload.contentServiceObserver
            contentServiceObserver.onStart(serviceContentTypeDownloadFromCache)
            contentServiceObserver.onSuccess(serviceContentTypeDownloadFromCache)
            return
        }
        // This will run if two request come for same url
        if(allRequestsByKey.containsKey(mKey))
        {
            serviceContentTypeDownload.comeFrom = "Cache"
            allRequestsByKey[mKey]!!.add(serviceContentTypeDownload)
            return
        }
        // Put it in the allRequestsByKey to manage it after done or cancel
        if(allRequestsByKey.containsKey(mKey))
        {
            allRequestsByKey[mKey]!!.add(serviceContentTypeDownload)
        } else
        {
            val lstMDDataType = LinkedList<ServiceContentTypeDownload>()
            lstMDDataType.add(serviceContentTypeDownload)
            allRequestsByKey[mKey] = lstMDDataType
        }
        // Create new WebCallDataTypeDownload by clone it from the parameter
        val newWebCallDataTypeDownload =
            serviceContentTypeDownload.getCopyOfMe(object : ContentServiceObserver
            {
                override fun onStart(serviceContentTypeDownload: ServiceContentTypeDownload)
                {
                    for(m in allRequestsByKey[serviceContentTypeDownload.keyMD5]!!)
                    {
                        m.data = serviceContentTypeDownload.data
                        m.contentServiceObserver.onStart(m)
                    }
                }

                override fun onSuccess(serviceContentTypeDownload: ServiceContentTypeDownload)
                {
                    for(m in allRequestsByKey[serviceContentTypeDownload.keyMD5]!!)
                    {
                        m.data = serviceContentTypeDownload.data
                        Log.e("HERRRR", m.comeFrom)
                        m.contentServiceObserver.onSuccess(m)
                    }
                    allRequestsByKey.remove(serviceContentTypeDownload.keyMD5)
                }

                override fun onFailure(serviceContentTypeDownload: ServiceContentTypeDownload,
                        statusCode: Int,
                        errorResponse: ByteArray?,
                        e: Throwable?)
                {
                    for(m in allRequestsByKey[serviceContentTypeDownload.keyMD5]!!)
                    {
                        m.data = serviceContentTypeDownload.data
                        m.contentServiceObserver.onFailure(m, statusCode, errorResponse, e)
                    }
                    allRequestsByKey.remove(serviceContentTypeDownload.keyMD5)
                }

                override fun onRetry(serviceContentTypeDownload: ServiceContentTypeDownload, retryNo: Int)
                {
                    for(m in allRequestsByKey[serviceContentTypeDownload.keyMD5]!!)
                    {
                        m.data = serviceContentTypeDownload.data
                        m.contentServiceObserver.onRetry(m, retryNo)
                    }
                }
            })
        // Get from download manager
        val contentServiceSyncTaskManager = ContentServiceSyncTaskManager()
        val client = contentServiceSyncTaskManager.get(newWebCallDataTypeDownload,
            object : ContentServiceStatusObserver
            {
                override fun setDone(serviceContentTypeDownload: ServiceContentTypeDownload)
                {
                    // put in the cache when mark as done
                    ContentServiceCachingManager.putMDownloadDataType(serviceContentTypeDownload.keyMD5,
                        serviceContentTypeDownload)
                    allRequestsClient.remove(serviceContentTypeDownload.keyMD5)
                }

                override fun onFailure(serviceContentTypeDownload: ServiceContentTypeDownload)
                {
                    allRequestsClient.remove(serviceContentTypeDownload.keyMD5)
                }

                override fun setCancelled(serviceContentTypeDownload: ServiceContentTypeDownload)
                {
                    allRequestsClient.remove(serviceContentTypeDownload.url)
                }
            })
        allRequestsClient[mKey] = client
    }

    fun cancelRequest(serviceContentTypeDownload: ServiceContentTypeDownload)
    {
        if(allRequestsByKey.containsKey(serviceContentTypeDownload.keyMD5))
        {
            allRequestsByKey[serviceContentTypeDownload.keyMD5]?.remove(serviceContentTypeDownload)
            serviceContentTypeDownload.comeFrom = "cancelRequest"
            serviceContentTypeDownload.contentServiceObserver.onFailure(serviceContentTypeDownload,
                0, null, null)
        }
    }

    fun clearTheCash()
    {
        ContentServiceCachingManager.clearDataCache()
    }

}
