@file:Suppress("DEPRECATION")

package com.muhdila.mygithubuser.ui

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.muhdila.mygithubuser.R

object NavBarColor {
    fun setColor(activity: AppCompatActivity, toolbar: Toolbar?) {
        val isLightTheme = activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
                Configuration.UI_MODE_NIGHT_NO

        val themeColor = if (isLightTheme) {
            R.color.md_theme_light_background
        } else {
            R.color.md_theme_dark_background
        }

        val statusBarColor = ContextCompat.getColor(activity, themeColor)
        val navigatorBarColor = ContextCompat.getColor(activity, themeColor)

        activity.window.decorView.systemUiVisibility =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (isLightTheme) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    0
                }
            } else {
                0
            }

        activity.window.statusBarColor = statusBarColor
        activity.window.navigationBarColor = navigatorBarColor

        toolbar?.setBackgroundColor(ContextCompat.getColor(activity, themeColor))
        activity.supportActionBar?.setBackgroundDrawable(ColorDrawable(statusBarColor))

        tintImages(activity, !isLightTheme)
    }

    private fun tintImages(activity: AppCompatActivity, isDarkTheme: Boolean) {
        val color = ContextCompat.getColor(
            activity,
            if (isDarkTheme) R.color.md_theme_light_background else R.color.md_theme_dark_background
        )

        activity.findViewById<ImageView>(R.id.img_setting)?.let {
            ImageViewCompat.setImageTintList(it, ColorStateList.valueOf(color))
        }

        activity.findViewById<ImageView>(R.id.img_fav)?.let {
            ImageViewCompat.setImageTintList(it, ColorStateList.valueOf(color))
        }
    }
}