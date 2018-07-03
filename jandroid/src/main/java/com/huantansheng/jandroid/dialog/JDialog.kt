package com.huantansheng.jandroid.dialog

import com.huantansheng.jandroid.R
import com.huantansheng.jandroid.dialog.jDialog.JDialogFragment

object JDialog {
    fun getLoadingDialog(): JDialogFragment {

        return JDialogFragment.init()
                .setLayoutId(R.layout.j_dialog_loading)
                .setWidth(-1) as JDialogFragment
    }
}