package com.saadfauzi.githubuser.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saadfauzi.githubuser.helpers.MyPreferences

class SettingsViewModelFactory(private val pref: MyPreferences): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class " + modelClass.name)
    }
}