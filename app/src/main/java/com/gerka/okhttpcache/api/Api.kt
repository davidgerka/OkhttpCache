package com.gerka.okhttpcache.api

import com.gerka.okhttpcache.bean.ListResponse
import com.gerka.okhttpcachelib.intercept.androidNetworkCache_10s
import com.gerka.okhttpcachelib.intercept.androidOfflineCache_10s
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("wxarticle/chapters/json?$androidNetworkCache_10s&$androidOfflineCache_10s")
    fun getList(): Observable<ListResponse>
}