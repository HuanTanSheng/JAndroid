package com.huantansheng.jandroid.dialog

import android.app.Dialog
import android.content.Context
import com.huantansheng.jandroid.R

class SimpleLoadingDialog(context: Context) : Dialog(context) {

    init {
        this.setContentView(R.layout.j_dialog_loading)
    }


}