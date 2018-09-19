package com.huantansheng.jandroid.sample.net


import com.huantansheng.jandroid.base.BaseApplication
import com.huantansheng.jandroid.dialog.SimpleLoadingDialog


object NetLoadingDialog {
    private var dialog: SimpleLoadingDialog? = null


    fun show() {
        if (dialog == null)
            dialog = SimpleLoadingDialog(BaseApplication.instance.applicationContext)

        dialog?.show()

    }

    fun cancel() {
        dialog?.dismiss()
    }
}