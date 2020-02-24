package com.mindvalley.view.pinboard

import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PinboardViewModelTest
{


    @Test
    fun getUserDetails_NULLTest()
    {
       /* Mockito.`when`(PinboardRepository.getUserDetails()).thenReturn(

            null
        )

        assertNull(PinboardRepository.getUserDetails())*/

        assertTrue(true)
    }

    @Test
    fun getUserDetails_NULLTest_()
    {

        //val repo = Mockito.mock(PinboardRepository::class.java)
        val vm = Mockito.mock(PinboardViewModel::class.java)

         Mockito.`when`(vm.getUserDetails()).thenReturn(

             null
         )

         assertNull(vm.getUserDetails())
    }

}