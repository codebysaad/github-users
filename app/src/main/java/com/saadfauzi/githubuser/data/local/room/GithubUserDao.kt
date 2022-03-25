package com.saadfauzi.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity

@Dao
interface GithubUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(githubUserEntity: List<GithubUserEntity>)

    @Update
    fun updateUser(githubUserEntity: GithubUserEntity)

    @Query("SELECT * FROM github_user ORDER BY id ASC")
    fun getAllGithubUsers(): LiveData<List<GithubUserEntity>>

    @Query("SELECT * FROM github_user WHERE favorite = 1")
    fun getFavoriteUsers(): LiveData<List<GithubUserEntity>>

    @Query("DELETE FROM github_user WHERE favorite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM github_user WHERE id = :id AND favorite = 1)")
    fun isFavorite(id: Int): Boolean
}