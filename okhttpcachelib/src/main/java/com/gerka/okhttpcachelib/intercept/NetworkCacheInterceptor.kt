package com.gerka.okhttpcachelib.intercept

import com.gerka.okhttpcachelib.intercept.NetworkCacheInterceptor.Companion.ANDROID_NETWORK_CACHE
import com.gerka.okhttpcachelib.util.parseLong
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


/**
 * 原理可查看这个贴：https://www.jianshu.com/p/dbda0bb8d541，感谢作者大大
 * OkHttp的在线缓存策略
 * 可以实现不同接口拥有不同缓存时间的拦截器
 * 使用步骤: 1、用addNetworkInterceptor方法添加
 *          2、在需要的接口里添加{androidNetworkCache_10s}即可
 */
class NetworkCacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val maxAge = getCacheMaxAge(request)
        val response = chain.proceed(request)
        return if (maxAge <= 0L) {
            response
        } else {
            response.newBuilder()
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public,max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }
    }

    companion object {
        const val ANDROID_NETWORK_CACHE = "androidnetworkcache"

    }
}

fun getCacheMaxAge(request: Request?): Long {
    return if (request == null) 0L else parseLong(request.url().queryParameter(ANDROID_NETWORK_CACHE))
}

//在线缓存过期时间
const val androidNetworkCache_10s = "${ANDROID_NETWORK_CACHE}=10"           //10秒
const val androidNetworkCache_15m = "${ANDROID_NETWORK_CACHE}=${60 * 15}"   //15分钟
const val androidNetworkCache_30m = "${ANDROID_NETWORK_CACHE}=${60 * 30}"   //30分钟
const val androidNetworkCache_60m = "${ANDROID_NETWORK_CACHE}=${60 * 60}"   //60分钟