package com.huantansheng.jandroid.sample.net

import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class RequestCallback<T> : FlowableSubscriber<ResponseWrapper<T>> {

    abstract fun success(data: T)
    abstract fun failure(statusCode: Int, message: String)

    private object Status {
        const val SUCCESS = 200
    }

    override fun onSubscribe(s: Subscription) {
        NetLoadingDialog.show()
    }

    override fun onNext(t: ResponseWrapper<T>) {
        if (t.code == Status.SUCCESS) {
            success(t.data)
            return
        }

        failure(t.code, t.message)
    }

    override fun onComplete() {
        NetLoadingDialog.cancel()
    }

    override fun onError(e: Throwable) {
        NetLoadingDialog.cancel()
        val msg: String = when (e) {
            is UnknownHostException -> "网络错误"
            is ConnectException -> "网络错误"
            is SocketTimeoutException -> "网络超时"
            else -> "网络错误"
        }
        failure(-1, msg)
    }
}
