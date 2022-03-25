package com.saadfauzi.githubuser.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity
import com.saadfauzi.githubuser.helpers.DummyData
import com.saadfauzi.githubuser.helpers.Result
import com.saadfauzi.githubuser.repository.GithubUserRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MainViewModelTest : TestCase() {

    @Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
    @get: Rule
    var instantTestExecutorRule = InstantTaskExecutorRule()
    private lateinit var mViewModel: MainViewModel
    private val repository: GithubUserRepository = mock(GithubUserRepository::class.java)

    @Before
    fun before() {
        mViewModel = MainViewModel(repository)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun testGetAllGithubUser() {
        val dummyUsers: Result<List<GithubUserEntity>> = Result.Success(DummyData.generateDummyUsers())
        val users: MutableLiveData<Result<List<GithubUserEntity>>> = MutableLiveData()
        users.value = dummyUsers

        `when`(repository.getAllGithubUser()).thenReturn(users)
        val observer: Observer<in Result<List<GithubUserEntity>>> = mock(Observer::class.java) as Observer<in Result<List<GithubUserEntity>>>

        mViewModel.getAllGithubUser().observeForever(observer)
        verify(observer).onChanged(dummyUsers)
    }
}