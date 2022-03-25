package com.saadfauzi.githubuser.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.saadfauzi.githubuser.R
import com.saadfauzi.githubuser.databinding.ActivitySplashBinding
import com.saadfauzi.githubuser.helpers.MyPreferences
import com.saadfauzi.githubuser.viewmodels.SettingsViewModel
import com.saadfauzi.githubuser.viewmodels.SettingsViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = MyPreferences.getInstance(dataStore)

        val viewModel = ViewModelProvider(this, SettingsViewModelFactory(pref))[
                SettingsViewModel::class.java
        ]

        viewModel.getStateDarkMode().observe(this) { isDarkMode ->
            if (isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.root?.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.grey_700, resources.newTheme()))
                binding?.icSplash?.let {
                    Glide.with(this)
                        .load(R.drawable.ic_github_night)
                        .override(250, 250)
                        .into(it)
                }
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.icSplash?.let {
                    Glide.with(this)
                        .load(R.drawable.ic_github)
                        .override(250, 250)
                        .into(it)
                }
            }
        }

        val delaySplashScreen = 2000L

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, delaySplashScreen)
    }
}