package com.muhdila.mygithubuser.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.muhdila.mygithubuser.R
import com.muhdila.mygithubuser.ui.NavBarColor
import com.muhdila.mygithubuser.ui.main.UserGithubActivity

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val delayMillis = 3000L

        // Animation
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, UserGithubActivity::class.java))
            finish()
        }, delayMillis)

        // Setting color status bar and navigator bar
        NavBarColor.setStatusBarAndNavBarColors(this)

        // Hide action bar
        supportActionBar?.hide()
    }
}