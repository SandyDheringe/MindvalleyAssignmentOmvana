package com.mindvalley.view.pinboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mindvalley.common.network.DataResult
import com.mindvalley.common.network.NetworkStatus
import com.mindvalley.view.pinboard.dto.*
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PinboardRepositoryTest
{

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp()
    {
    }

    @Test
    fun getUserDetails_Check_Error()
    {
        val data = MutableLiveData<DataResult<ArrayList<MasterDetails>>>()
        data.value = DataResult.setError(null)

        mockkObject(PinboardRepository)
        every { PinboardRepository.getUserDetails() } returns data

        Assert.assertEquals(PinboardRepository.getUserDetails().value?.networkStatus, NetworkStatus.ERROR)
    }

    @Test
    fun getUserDetails_Success()
    {
        val profileImage = ProfileImage("http://small","","")
        val linkDetails = LinkDetails("","","","")
        val user = UserDetails("1","sandy","sandeep",profileImage,linkDetails)
        val urlDetails = UrlDetails("","","","","")
        val master  = MasterDetails("1","",1,1,"",12,true,user,urlDetails,null, linkDetails)
        val data = MutableLiveData<DataResult<ArrayList<MasterDetails>>>()

        data.value = DataResult.setResult(arrayListOf(master))

        mockkObject(PinboardRepository)
        every { PinboardRepository.getUserDetails() } returns data

        Assert.assertEquals(PinboardRepository.getUserDetails().value?.networkStatus, NetworkStatus.SUCCESS)
    }

}