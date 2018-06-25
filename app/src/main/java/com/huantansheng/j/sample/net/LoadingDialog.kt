package com.huantansheng.j.sample.net

import android.content.Context
import com.huantansheng.j.dialog.JDialog
import com.huantansheng.j.dialog.jDialog.JDialogFragment


object LoadingDialog {
    private var dialog: JDialogFragment? = null

    fun show(context: Context) {
        dialog = JDialog.getLoadingDialog()
    }

    fun cancel() {
        dialog?.dismiss()
    }
}