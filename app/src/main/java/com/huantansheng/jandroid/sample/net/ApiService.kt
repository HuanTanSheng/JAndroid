package com.huantansheng.jandroid.sample.net

import io.reactivex.Flowable
import retrofit2.http.POST

interface ApiService {

    @POST("sdf")
    fun getData(): Flowable<ResponseWrapper<Item>>

}