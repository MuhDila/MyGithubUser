package com.muhdila.mygithubuser.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.muhdila.mygithubuser.R
import com.muhdila.mygithubuser.ui.NavBarColor
import com.muhdila.mygithubuser.ui.main.UserGithubActivity
import com.muhdila.mygithubuser.ui.setting.SettingPreferences
import com.muhdila.mygithubuser.ui.setting.SettingViewModel
import com.muhdila.mygithubuser.ui.setting.ViewModelFactory
import com.muhdila.mygithubuser.ui.setting.dataStore

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val delayMillis = 3000L

        // Animation
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, UserGithubActivity::class.java))
            finish()
        }, delayMillis)

        // Setting color status bar and navigator bar
        NavBarColor.setColor(this, null)

        // Hide action bar
        supportActionBar?.hide()

        // Theme
        settingTheme()
    }

    private fun settingTheme() {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
