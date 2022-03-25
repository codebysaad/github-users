package com.saadfauzi.githubuser.data.remote.rest

import com.saadfauzi.githubuser.data.remote.models.DetailUser
import com.saadfauzi.githubuser.data.remote.models.GithubUserItem
import com.saadfauzi.githubuser.data.remote.models.SearchUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(): Call<ArrayList<GithubUserItem>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getListFollowers(@Path("username") username: String?): Call<ArrayList<GithubUserItem>>

    @GET("users/{username}/following")
    fun getListFollowing(@Path("username") username: String?): Call<ArrayList<GithubUserItem>>

    @GET("search/users")
    fun searchUser(@Query("q") username: String?): Call<SearchUser>
}