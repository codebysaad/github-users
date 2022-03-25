package com.saadfauzi.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saadfauzi.githubuser.data.remote.models.DetailUser
import com.saadfauzi.githubuser.data.remote.rest.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailsGithubUser = MutableLiveData<DetailUser>()
    val detailsGithubUser: LiveData<DetailUser> = _detailsGithubUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadDetailsUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _detailsGithubUser.value = response.body()
                        Log.i(TAG, "onResponse: ${responseBody.bio}")
                    }else{
                        Log.e(TAG, "onFailed: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailed: ${t.message}")
            }

        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}