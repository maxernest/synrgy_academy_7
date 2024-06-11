package com.example.androidapp

import com.example.androidapp.data.local.entity.User
import com.example.androidapp.data.remote.UserDao
import com.example.androidapp.presentation.viewModel.UserViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UserViewModelUnitTest {

    private lateinit var viewModel: UserViewModel
    private lateinit var dao: UserDao

    val dispatcher  = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
        dao = mockk()
        viewModel = UserViewModel(dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insertUser():Unit = runBlocking {
        every{
            runBlocking {
                dao.insertAll(User(username = "", password = "", email = ""))
            }
        } returns Unit

        viewModel.insertUser(User(username = "", password = "", email = ""))

        verify {
            runBlocking { dao.insertAll(User(username = "", password = "", email = "")) }
        }
    }

    @Test
    fun findByEmailAndPassword():Unit = runBlocking {
        val result = mockk<User>()
        every{
            runBlocking {
                dao.findByEmailAndPassword("","")
            }
        } returns result

        viewModel.findByEmailAndPassword("","")

        verify {
            runBlocking { dao.findByEmailAndPassword("","") }
        }
    }

    @Test
    fun findUserById():Unit = runBlocking {
        val result = mockk<User>()
        every{
            runBlocking {
                dao.findUserById(1)
            }
        } returns result

        viewModel.findUserById(1)

        verify {
            runBlocking { dao.findUserById(1) }
        }
    }

    @Test
    fun updateUser():Unit = runBlocking {
        every{
            runBlocking {
                dao.updateUser(1,"","","","")
            }
        } returns Unit

        viewModel.updateUser(1,"","","","")

        verify {
            runBlocking { dao.updateUser(1,"","","","") }
        }
    }
}