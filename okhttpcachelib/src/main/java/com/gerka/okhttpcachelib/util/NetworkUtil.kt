package com.gerka.okhttpcachelib.util

import android.content.Context
import android.net.ConnectivityManager

fun isNetworkAvailable(context: Context?): Boolean {
    if (context != null) {
        val conManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable
    }
    return true
}