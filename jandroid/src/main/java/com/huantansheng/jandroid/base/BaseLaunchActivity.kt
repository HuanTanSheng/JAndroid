package com.huantansheng.jandroid.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View

/**
 * Created by huan on 2018/6/25.
 */

abstract class BaseLaunchActivity : BaseActivity() {
    private lateinit var animator: ObjectAnimator
    private var isCancel = false

    //动画展示时间，2300
    protected abstract val animatorDuration: Long

    //ReStart时动画展示时间，1000
    protected abstract val restartAnimatorDuration: Long

    //动画view
    protected abstract val animatorView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animator = ObjectAnimator.ofFloat(animatorView, "Alpha", 1.0f, 0.7f)
        animator.duration = animatorDuration
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (isCancel) {
                    return
                }
                toStart()
            }
        })
        animator.start()
    }


    override fun onStop() {
        if (animator.isRunning) {
            isCancel = true
            animator.cancel()
        }
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        isCancel = false
        animator.duration = restartAnimatorDuration
        animator.start()
    }

    //启动下一界面
    protected abstract fun toStart()
}
