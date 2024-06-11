package com.example.androidapp

import com.example.androidapp.data.local.DataStoreManager
import com.example.androidapp.presentation.viewModel.DataStoreViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataStoreUnitTest {

    private lateinit var viewModel: DataStoreViewModel
    private lateinit var pref: DataStoreManager

    @Before
    fun setUp(){
        pref = mockk()
        viewModel = DataStoreViewModel(pref)
    }

    @Test
    fun saveDataStore():Unit = runBlocking {
        every{
            runBlocking {
                pref.setUser(1)
                pref.setAcount(1)
            }
        } returns Unit

        viewModel.saveDataStore(1,1)

        verify {
            runBlocking {
                pref.setUser(1)
                pref.setAcount(1)
            }
        }
    }

    @Test
    fun getUser():Unit = runBlocking {
        val result = mockk<Flow<Int>>()
        every{
            runBlocking {
                pref.getUser()
            }
        } returns result

        viewModel.getUser()

        verify {
            runBlocking {
                pref.getUser()
            }
        }
    }

    @Test
    fun getAccount():Unit = runBlocking {
        val result = mockk<Flow<Int>>()
        every{
            runBlocking {
                pref.getAccount()
            }
        } returns result

        viewModel.getAccount()

        verify {
            runBlocking {
                pref.getAccount()
            }
        }
    }
}