package com.saadfauzi.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity
import com.saadfauzi.githubuser.repository.GithubUserRepository

class MainViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    fun getAllGithubUser() = githubUserRepository.getAllGithubUser()

    fun getSearchUser(query: String?) = githubUserRepository.getSearchUser(query)

    fun getFavoriteUser() = githubUserRepository.getFavoriteUser()

    fun saveUser(githubUserEntity: GithubUserEntity) {
        githubUserRepository.setFavoriteUser(githubUserEntity, true)
        Log.d("MainViewModel", githubUserEntity.username)
    }

    fun deleteUser(githubUserEntity: GithubUserEntity) {
        githubUserRepository.setFavoriteUser(githubUserEntity, false)
    }
}