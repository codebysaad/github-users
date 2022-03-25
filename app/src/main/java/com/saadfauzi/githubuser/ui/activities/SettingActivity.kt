package com.saadfauzi.githubuser.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.saadfauzi.githubuser.databinding.ActivitySettingBinding
import com.saadfauzi.githubuser.helpers.MyPreferences
import com.saadfauzi.githubuser.viewmodels.SettingsViewModel
import com.saadfauzi.githubuser.viewmodels.SettingsViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {

    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = MyPreferences.getInstance(dataStore)

        val viewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        viewModel.getStateDarkMode().observe(this) { isDarkMode ->
            if (isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.swDarkMode?.isChecked = true
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.swDarkMode?.isChecked = false
            }
        }

        binding?.swDarkMode?.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveStateDarkMode(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}