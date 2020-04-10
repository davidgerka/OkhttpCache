package com.gerka.okhttpcache.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        if (response.networkResponse() != null) {
            Log.i("tag", "##-------network")
        } else if (response.cacheResponse() != null) {
            Log.i("tag", "##-------cache")
        }
        return response
    }
}