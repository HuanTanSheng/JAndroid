package com.huantansheng.jandroid.sample.net

import android.content.Context
import com.huantansheng.jandroid.dialog.JDialog
import com.huantansheng.jandroid.dialog.jDialog.JDialogFragment


object LoadingDialog {
    private var dialog: JDialogFragment? = null

    fun show(context: Context) {
        dialog = JDialog.getLoadingDialog()
    }

    fun cancel() {
        dialog?.dismiss()
    }
}