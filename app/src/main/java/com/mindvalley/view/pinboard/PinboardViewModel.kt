package com.mindvalley.view.pinboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mindvalley.common.network.DataResult
import com.mindvalley.view.pinboard.dto.MasterDetails

open class PinboardViewModel : ViewModel()
{

    fun getUserDetails(): LiveData<DataResult<ArrayList<MasterDetails>>>
    {
        return PinboardRepository.getUserDetails()
    }

    fun clearCache()
    {
        PinboardRepository.clearCache()
    }

}