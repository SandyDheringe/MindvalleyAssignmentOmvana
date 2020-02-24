package com.mindvalley.view.pinboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PinboardViewModelFactory : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return modelClass.getConstructor().newInstance()
    }

}