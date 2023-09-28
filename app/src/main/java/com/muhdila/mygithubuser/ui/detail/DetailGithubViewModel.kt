package com.muhdila.mygithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhdila.mygithubuser.data.remote.response.UserGithubResponseDetail
import com.muhdila.mygithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailGithubViewModel : ViewModel() {

    private val _userGithubDetail = MutableLiveData<UserGithubResponseDetail>()
    val userGithubDetail: LiveData<UserGithubResponseDetail> = _userGithubDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun userGithubDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUserDetail(username)
        client.enqueue(object : Callback<UserGithubResponseDetail> {
            override fun onResponse(
                call: Call<UserGithubResponseDetail>,
                response: Response<UserGithubResponseDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userGithubDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithubResponseDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val USERNAME = "Username"
        const val AVATAR_URL = "Avatar_URL"
        const val HOME_URL = "Home_URL"
        const val TAG = "DetailViewModel"
    }
}
