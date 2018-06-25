package com.huantansheng.j.retrofit


import com.huantansheng.j.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit配置文件
 * 需在app的module中配置具体参数及各种转换操作
 * Created by huan on 2018/6/25.
 */
class ApiClient private constructor() {

    lateinit var retrofit: Retrofit

    private object Holder {
        val INSTANCE = ApiClient()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }


    fun initRetrofit(baseUrl: String) {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
//                .retryOnConnectionFailure(true)
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        retrofit = Retrofit.Builder()
                .baseUrl("$baseUrl/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}