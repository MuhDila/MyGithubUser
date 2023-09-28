package com.muhdila.mygithubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserGithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<UserGithubItems>
)

data class UserGithubItems(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("html_url")
	val homeUrl: String?,

	@field:SerializedName("avatar_url")
	val avatarUrl: String
)