package com.saadfauzi.githubuser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity
import com.saadfauzi.githubuser.data.local.room.GithubUserDao
import com.saadfauzi.githubuser.data.remote.models.GithubUserItem
import com.saadfauzi.githubuser.data.remote.models.SearchUser
import com.saadfauzi.githubuser.data.remote.rest.ApiService
import com.saadfauzi.githubuser.helpers.AppExecutors
import com.saadfauzi.githubuser.helpers.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserRepository private constructor(
    private val apiService: ApiService,
    private val githubUserDao: GithubUserDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<GithubUserEntity>>>()
    private val searchResult = MediatorLiveData<Result<List<GithubUserEntity>>>()

    fun getAllGithubUser(): LiveData<Result<List<GithubUserEntity>>> {
        result.value = Result.Loading
        val client = apiService.getUsers()
        client.enqueue(object : Callback<ArrayList<GithubUserItem>> {
            override fun onResponse(
                call: Call<ArrayList<GithubUserItem>>,
                response: Response<ArrayList<GithubUserItem>>
            ) {
                if (response.isSuccessful){
                    val userList = ArrayList<GithubUserEntity>()
                    appExecutors.diskIO.execute {
                        response.body()?.forEach { user ->
                            val isFavorite = githubUserDao.isFavorite(user.id)
                            val data = GithubUserEntity(
                                user.id,
                                user.followingUrl,
                                user.username,
                                user.followersUrl,
                                user.avatarUrl,
                                user.htmlUrl,
                                isFavorite
                            )
                            userList.add(data)
                        }
                        githubUserDao.deleteAll()
                        githubUserDao.insertUser(userList)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubUserItem>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localSource = githubUserDao.getAllGithubUsers()
        result.addSource(localSource) { newData: List<GithubUserEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getSearchUser(query: String?): LiveData<Result<List<GithubUserEntity>>> {
        searchResult.value = Result.Loading
        val client = apiService.searchUser(query)
        client.enqueue(object : Callback<SearchUser> {
            override fun onResponse(call: Call<SearchUser>, response: Response<SearchUser>) {
                if (response.isSuccessful) {
                    val listOfUser = ArrayList<GithubUserEntity>()
                    appExecutors.diskIO.execute {
                        response.body()?.items?.forEach { user ->
                            val isFavorite = githubUserDao.isFavorite(user.id)
                            val data = GithubUserEntity(
                                user.id,
                                user.followingUrl,
                                user.username,
                                user.followersUrl,
                                user.avatarUrl,
                                user.htmlUrl,
                                isFavorite
                            )
                            listOfUser.add(data)
                        }
                        githubUserDao.deleteAll()
                        githubUserDao.insertUser(listOfUser)
                    }
                }
            }

            override fun onFailure(call: Call<SearchUser>, t: Throwable) {
                searchResult.value = Result.Error(t.message.toString())
            }
        })
        val localSource = githubUserDao.getAllGithubUsers()
        searchResult.addSource(localSource) { newData ->
            searchResult.value = Result.Success(newData)
        }
        return searchResult
    }

    fun getFavoriteUser(): LiveData<List<GithubUserEntity>>{
        return githubUserDao.getFavoriteUsers()
    }

    fun setFavoriteUser(githubUserEntity: GithubUserEntity, favoriteState: Boolean){
        appExecutors.diskIO.execute {
            githubUserEntity.isFavorite = favoriteState
            githubUserDao.updateUser(githubUserEntity)
        }
    }

    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            githubUserDao: GithubUserDao,
            appExecutors: AppExecutors
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, githubUserDao, appExecutors)
            }.also { instance = it }
    }
}