package com.huantansheng.jandroid.sample.net


import com.huantansheng.jandroid.retrofit.ApiClient
import io.reactivex.Flowable


object Api {
    private val service = ApiClient.retrofit.create(ApiService::class.java)

    fun getData():Flowable<ResponseWrapper<Item>>{
        return service.getData()
    }
}