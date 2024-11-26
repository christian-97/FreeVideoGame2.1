package com.example.freevideogame

import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController

object SystemBarsUtil {
    fun hideSystemBars(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}