package com.huantansheng.j.sample.net

import io.reactivex.Flowable
import retrofit2.http.POST

interface ApiService {

    @POST("sdf")
    fun getData(): Flowable<ResponseWrapper<Item>>

}