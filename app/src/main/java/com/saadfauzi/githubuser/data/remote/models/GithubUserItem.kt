package com.saadfauzi.githubuser.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	) : Parcelable
