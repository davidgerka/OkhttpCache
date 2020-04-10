package com.gerka.okhttpcache.interceptor

import com.gerka.okhttpcachelib.intercept.getCacheMaxAge
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 给项目统一添加随机参数的拦截器
 */
class RanParaInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //这里模拟项目需求：添加一个随机参数ran，就会导致缓存失败
        val oldRequest = chain.request()
        val builder = oldRequest.url().newBuilder()
            .addEncodedQueryParameter("ran", System.currentTimeMillis().toString())
        val newRequest = oldRequest.newBuilder().url(builder.build()).build()

        //所以，如果项目有统一添加随机参数的拦截器，就需要做以下判断处理
        val maxAge = getCacheMaxAge(oldRequest)
        if (maxAge > 0) { //该接口需要缓存数据
            val mBuilder = newRequest.url().newBuilder()
                .removeAllQueryParameters("ran")    //移除ran
            val mRequest = newRequest.newBuilder().url(mBuilder.build()).build()
            return chain.proceed(mRequest)
        }
        return chain.proceed(newRequest)


//        //也可以写得简单点
//        var request = chain.request()
//        val maxAge = getCacheMaxAge(request)
//        if (maxAge <= 0) { //不需要缓存数据的接口才添加ran
//            val builder = request.url().newBuilder()
//                .addEncodedQueryParameter("ran", System.currentTimeMillis().toString())
//            request = request.newBuilder().url(builder.build()).build()
//        }
//        return chain.proceed(request)
    }
}