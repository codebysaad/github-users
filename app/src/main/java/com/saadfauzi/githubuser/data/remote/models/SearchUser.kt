package com.saadfauzi.githubuser.data.remote.models

import com.google.gson.annotations.SerializedName

data class SearchUser(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: ArrayList<GithubUserItem>
)