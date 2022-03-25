package com.saadfauzi.githubuser.di

import android.content.Context
import com.saadfauzi.githubuser.data.local.room.GithubUserDatabase
import com.saadfauzi.githubuser.data.remote.rest.ApiConfig
import com.saadfauzi.githubuser.helpers.AppExecutors
import com.saadfauzi.githubuser.repository.GithubUserRepository

object Injection {
    fun provideRepository(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubUserDatabase.getInstance(context)
        val dao = database.githubUserDao()
        val appExecutors = AppExecutors()
        return GithubUserRepository.getInstance(apiService, dao, appExecutors)
    }
}