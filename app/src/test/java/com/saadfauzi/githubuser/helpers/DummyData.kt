package com.saadfauzi.githubuser.helpers

import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity

object DummyData {

    fun generateDummyUsers(): ArrayList<GithubUserEntity> {
        val userEntities: ArrayList<GithubUserEntity> = ArrayList()

        userEntities.add(
            GithubUserEntity(
                1,
                "https://api.github.com/users/mojombo/following{/other_user}",
                "mojombo",
                "https://api.github.com/users/mojombo/followers",
                "https://avatars.githubusercontent.com/u/1?v=4",
                "https://github.com/mojombo",
                false
            )
        )
        userEntities.add(
            GithubUserEntity(
                2,
                "https://api.github.com/users/defunkt/following{/other_user}",
                "defunkt",
                "https://api.github.com/users/defunkt/followers",
                "https://avatars.githubusercontent.com/u/2?v=4",
                "https://github.com/defunkt",
                false
            )
        )
        return userEntities
    }
}