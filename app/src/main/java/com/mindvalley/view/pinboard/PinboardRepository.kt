package com.mindvalley.view.pinboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mindvalley.common.network.DataResult
import com.mindvalley.view.pinboard.dto.MasterDetails
import com.mindvalley.imageloader.callback.ContentServiceObserver
import com.mindvalley.imageloader.models.ServiceContentTypeDownload
import com.mindvalley.imageloader.models.ServiceJsonDownload
import com.mindvalley.imageloader.utilities.ContentTypeServiceDownload
import com.mindvalley.utility.Constants
import java.nio.charset.StandardCharsets

object PinboardRepository
{

    fun getUserDetails(): LiveData<DataResult<ArrayList<MasterDetails>>>
    {
        val data = MutableLiveData<DataResult<ArrayList<MasterDetails>>>()
        val mDataTypeJson: ServiceContentTypeDownload =
            ServiceJsonDownload(Constants.API_URL, object : ContentServiceObserver
            {

                override fun onSuccess(serviceContentTypeDownload: ServiceContentTypeDownload)
                {
                    val response = String(serviceContentTypeDownload.data!!, StandardCharsets.UTF_8)
                    val detailsResponses: ArrayList<MasterDetails> =
                        Gson().fromJson(response, object : TypeToken<ArrayList<MasterDetails>>()
                        {}.type)
                    data.value = DataResult.setResult(detailsResponses)
                }

                override fun onFailure(serviceContentTypeDownload: ServiceContentTypeDownload,
                        statusCode: Int, errorResponse: ByteArray?, e: Throwable?)
                {
                    data.value = DataResult.setError(e, statusCode)
                }

            })
        ContentTypeServiceDownload.getRequest(mDataTypeJson)
        return data
    }

    fun clearCache()
    {
        ContentTypeServiceDownload.clearTheCash()
    }

}