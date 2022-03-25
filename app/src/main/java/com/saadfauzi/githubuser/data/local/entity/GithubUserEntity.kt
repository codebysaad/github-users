package com.saadfauzi.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "github_user")
class GithubUserEntity (

    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    val id: Int,

    @field:ColumnInfo(name = "following_url")
    val followingUrl: String,

    @field:ColumnInfo(name = "login")
    val username: String,

    @field:ColumnInfo(name = "followers_url")
    val followersUrl: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean
): Parcelable