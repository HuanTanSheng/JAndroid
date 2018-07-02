package com.huantansheng.j.sample.net


import com.huantansheng.j.retrofit.ApiClient
import io.reactivex.Flowable


object Api {
    private val service = ApiClient.retrofit.create(ApiService::class.java)

    fun getData():Flowable<ResponseWrapper<Item>>{
        return service.getData()
    }
}