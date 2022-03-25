package com.saadfauzi.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saadfauzi.githubuser.data.remote.models.GithubUserItem
import com.saadfauzi.githubuser.data.remote.rest.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFollowingViewModel : ViewModel() {

    private val _listFollowers = MutableLiveData<ArrayList<GithubUserItem>>()
    val listFollowers: LiveData<ArrayList<GithubUserItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<ArrayList<GithubUserItem>>()
    val listFollowing: LiveData<ArrayList<GithubUserItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListFollowers(username: String?){
        _isLoading.value = true
        Log.d(TAG, "Username is: $username")
        val client = ApiConfig.getApiService().getListFollowers(username)
        client.enqueue(object : Callback<ArrayList<GithubUserItem>>{
            override fun onResponse(call: Call<ArrayList<GithubUserItem>>, response: Response<ArrayList<GithubUserItem>>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    if (response.body() != null){
                        _listFollowers.value = response.body()
                        Log.i(TAG, response.body()?.size.toString())
                    }else{
                        Log.e(TAG, response.message())
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }

        })
    }

    fun getListFollowing(username: String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowing(username)
        client.enqueue(object : Callback<ArrayList<GithubUserItem>> {
            override fun onResponse(call: Call<ArrayList<GithubUserItem>>, response: Response<ArrayList<GithubUserItem>>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    if (response.body() != null){
                        _listFollowing.value = response.body()
                        Log.i(TAG, response.body()?.size.toString())
                    }else{
                        Log.e(TAG, response.message())
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }

        })
    }

    companion object{
        private const val TAG = "ListFollowersViewModel"
    }
}