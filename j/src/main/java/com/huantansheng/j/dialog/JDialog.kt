package com.huantansheng.j.dialog

import com.huantansheng.j.R
import com.huantansheng.j.dialog.jDialog.JDialogFragment

object JDialog {
    fun getLoadingDialog(): JDialogFragment {

        return JDialogFragment.init()
                .setLayoutId(R.layout.j_dialog_loading)
                .setWidth(-1) as JDialogFragment
    }
}