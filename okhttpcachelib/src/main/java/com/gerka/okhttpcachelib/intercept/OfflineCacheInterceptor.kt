package com.gerka.okhttpcachelib.intercept

import android.app.Application
import com.gerka.okhttpcachelib.intercept.OfflineCacheInterceptor.Companion.ANDROID_OFFLINE_CACHE
import com.gerka.okhttpcachelib.util.isNetworkAvailable
import com.gerka.okhttpcachelib.util.parseLong
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


/**
 * 原理可查看这个贴：https://www.jianshu.com/p/dbda0bb8d541，感谢作者大大
 * OkHttp的离线缓存策略，必须配合{NetworkCacheInterceptor}一起使用
 * 可以实现不同接口拥有不同缓存时间的拦截器
 * 使用步骤: 1、确保添加了NetworkCacheInterceptor拦截器
 *          2、用addInterceptor方法添加
 *          3、在需要的接口里添加{androidOfflineCache_30s}即可
 */
class OfflineCacheInterceptor(private val app: Application) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val maxStale = getCacheMaxStale(request)
        if (maxStale > 0 && !isNetworkAvailable(app)) {
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
            val response = chain.proceed(request)
            return if (response.code() == 504) {
                chain.proceed(chain.request())  //没有缓存或者缓存失效，就返回源请求的结果
            } else {
                response
            }
        }
        return chain.proceed(request)
    }

    private fun getCacheMaxStale(request: Request?): Long {
        return if (request == null) 0L else parseLong(
            request.url().queryParameter(
                ANDROID_OFFLINE_CACHE
            )
        )
    }

    companion object {
        const val ANDROID_OFFLINE_CACHE = "androidofflinecache"
    }
}

//离线缓存过期时间（离线时，总的缓存过期时间 = 在线缓存过期时间 + 离线缓存过期时间）
const val androidOfflineCache_10s = "${ANDROID_OFFLINE_CACHE}= 10"           //10秒
const val androidOfflineCache_30s = "${ANDROID_OFFLINE_CACHE}=30"           //30秒
const val androidOfflineCache_10m = "${ANDROID_OFFLINE_CACHE}=${60 * 10}"   //10分钟
const val androidOfflineCache_60m = "${ANDROID_OFFLINE_CACHE}=${60 * 60}"   //60分钟
const val androidOfflineCache_1d = "${ANDROID_OFFLINE_CACHE}=${60 * 60 * 24}"       //1天
const val androidOfflineCache_7d = "${ANDROID_OFFLINE_CACHE}=${60 * 60 * 24 * 7}"   //7天