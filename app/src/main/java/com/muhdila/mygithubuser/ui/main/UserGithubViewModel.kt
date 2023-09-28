package com.muhdila.mygithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhdila.mygithubuser.data.remote.response.UserGithubItems
import com.muhdila.mygithubuser.data.remote.response.UserGithubResponse
import com.muhdila.mygithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserGithubViewModel : ViewModel() {

    private val _userGithubList = MutableLiveData<List<UserGithubItems>>()
    val userGithubList: LiveData<List<UserGithubItems>> = _userGithubList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userGithubSearch = MutableLiveData<List<UserGithubItems>>()
    val userGithubSearch: LiveData<List<UserGithubItems>> = _userGithubSearch

    fun userGithubList() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUserRandom()
        client.enqueue(object: Callback<List<UserGithubItems>> {
            override fun onResponse(
                call: Call<List<UserGithubItems>>,
                response: Response<List<UserGithubItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userGithubList.value = response.body()
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

    fun userGithubSearch(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(query)
        client.enqueue(object: Callback<UserGithubResponse> {
            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userGithubSearch.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }
        })
    }

    companion object {
        private const val TAG = "UserGithubViewModel"
    }
}
