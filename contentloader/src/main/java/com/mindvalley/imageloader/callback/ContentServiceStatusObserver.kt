package com.mindvalley.imageloader.callback

import com.mindvalley.imageloader.models.ServiceContentTypeDownload

interface ContentServiceStatusObserver
{
    fun setDone(serviceContentTypeDownload: ServiceContentTypeDownload)

    fun setCancelled(serviceContentTypeDownload: ServiceContentTypeDownload)

    fun onFailure(serviceContentTypeDownload: ServiceContentTypeDownload)
}
