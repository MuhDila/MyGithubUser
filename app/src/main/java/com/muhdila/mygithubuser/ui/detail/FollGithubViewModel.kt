package com.muhdila.mygithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhdila.mygithubuser.data.remote.response.UserGithubItems
import com.muhdila.mygithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollGithubViewModel: ViewModel() {

    private val _listGithubFollower = MutableLiveData<List<UserGithubItems>>()
    val listUserGithubFollower: LiveData<List<UserGithubItems>> = _listGithubFollower

    private val _listGithubFollowing = MutableLiveData<List<UserGithubItems>>()
    val listUserGithubFollowing: LiveData<List<UserGithubItems>> = _listGithubFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun userGithubFollower(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubFollower(name)
        client.enqueue(object : Callback<List<UserGithubItems>> {
            override fun onResponse(
                call: Call<List<UserGithubItems>>,
                response: Response<List<UserGithubItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listGithubFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UserGithubItems>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun userGithubFollowing(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubFollowing(name)
        client.enqueue(object: Callback<List<UserGithubItems>> {
            override fun onResponse(
                call: Call<List<UserGithubItems>>,
                response: Response<List<UserGithubItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listGithubFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UserGithubItems>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "DetailViewModel"
    }
}
