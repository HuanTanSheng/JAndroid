package com.huantansheng.jandroid.sample.net

import android.app.Dialog
import io.reactivex.FlowableSubscriber
import io.reactivex.subscribers.DisposableSubscriber
import org.reactivestreams.Subscription
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class RequestCallback<T> : DisposableSubscriber<ResponseWrapper<T>>() {


    abstract fun success(data: T)
    abstract fun failure(statusCode: Int, message: String)
    abstract fun getDialog():Dialog?

    private object Status {
        const val SUCCESS = 200
    }

    override fun onNext(t: ResponseWrapper<T>) {
        if (null != getDialog()) {
            getDialog()!!.dismiss()
        }
        if (t.code == Status.SUCCESS) {
            success(t.data)
            return
        }

        failure(t.code, t.message)
    }

    override fun onComplete() {
        if (null != getDialog()) {
            getDialog()!!.dismiss()
        }
    }

    override fun onError(e: Throwable) {
        if (null != getDialog()) {
            getDialog()!!.dismiss()
        }
        val msg: String = when (e) {
            is UnknownHostException -> "网络错误"
            is ConnectException -> "网络错误"
            is SocketTimeoutException -> "网络超时"
            else -> "网络错误"
        }
        failure(-1, msg)
    }
}
