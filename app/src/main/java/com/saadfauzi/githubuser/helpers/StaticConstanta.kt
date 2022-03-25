package com.saadfauzi.githubuser.helpers

import com.saadfauzi.githubuser.BuildConfig

open class StaticConstanta {
    companion object{
        const val GITHUB_API = "https://api.github.com/"
        const val ACCESS_TOKEN = BuildConfig.API_KEY
    }
}