package com.muhdila.mygithubuser.data.retrofit

import com.muhdila.mygithubuser.data.response.UserGithubItems
import com.muhdila.mygithubuser.data.response.UserGithubResponse
import com.muhdila.mygithubuser.data.response.UserGithubResponseDetail
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getGithubUserRandom(): Call<List<UserGithubItems>>

    @GET("search/users")
    fun getGithubUser(@Query("q") q: String): Call<UserGithubResponse>

    @GET("users/{name}")
    fun getGithubUserDetail(@Path("name") name: String) : Call<UserGithubResponseDetail>

    @GET("users/{name}/followers")
    fun getGithubFollower(@Path("name") name: String): Call<List<UserGithubItems>>

    @GET("users/{name}/following")
    fun getGithubFollowing(@Path("name") name: String) : Call<List<UserGithubItems>>
}