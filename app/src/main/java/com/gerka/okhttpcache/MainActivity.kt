package com.gerka.okhttpcache

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gerka.okhttpcache.api.Api
import com.gerka.okhttpcache.bean.ListResponse
import com.gerka.okhttpcache.interceptor.LogInterceptor
import com.gerka.okhttpcache.interceptor.RanParaInterceptor
import com.gerka.okhttpcachelib.intercept.NetworkCacheInterceptor
import com.gerka.okhttpcachelib.intercept.OfflineCacheInterceptor
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        getRetrofit("http://www.wanandroid.com")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        retrofit.create(Api::class.java).getList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ListResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ListResponse) {
                    tv_result.text = t.toString()
                }

                override fun onError(e: Throwable) {
                    Log.e("tag", "##-------msg = " + e.message)
                }

            })
    }

    private fun getOkHttpClient(): OkHttpClient {
        val cacheDirectory = File(application.cacheDir, "okhttpcache")
        val cache = Cache(cacheDirectory, (100 * 1024 * 1024).toLong())
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(LogInterceptor())
            .addInterceptor(RanParaInterceptor())                   //添加随机参数拦截器
//            .addInterceptor(OfflineCacheInterceptor(application))   //添加离线缓存拦截器
            .addNetworkInterceptor(NetworkCacheInterceptor())       //添加在线缓存拦截器
            .cache(cache)
            .build()
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}
