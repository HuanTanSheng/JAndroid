package com.huantansheng.jandroid.dialog

import com.huantansheng.jandroid.R
import com.othershe.nicedialog.NiceDialog

object JDialog {
    fun getLoadingDialog(): NiceDialog {

        return NiceDialog.init()
                .setLayoutId(R.layout.j_dialog_loading)
                .setWidth(-1) as NiceDialog
    }
}