package com.huantansheng.j.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.huantansheng.j.dialog.jDialog.JDialogFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JDialogFragment.init()
                .setLayoutId(R.layout.j_dialog_loading)
                .setWidth(-1)
                .show(supportFragmentManager)
    }
}
