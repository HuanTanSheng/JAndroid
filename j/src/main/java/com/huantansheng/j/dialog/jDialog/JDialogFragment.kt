package com.huantansheng.j.dialog.jDialog

import android.os.Bundle
import android.support.annotation.LayoutRes

class JDialogFragment : BaseJDialogFragment() {
    private var convertListener: ViewConvertListener? = null

    override fun intLayoutId(): Int {
        return layoutId
    }

    override fun convertView(holder: ViewHolder, dialogFragment: BaseJDialogFragment) {
        if (convertListener != null) {
            convertListener!!.convertView(holder, dialogFragment)
        }
    }


    fun setLayoutId(@LayoutRes layoutId: Int): JDialogFragment {
        this.layoutId = layoutId
        return this
    }

    fun setConvertListener(convertListener: ViewConvertListener): JDialogFragment {
        this.convertListener = convertListener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            convertListener = savedInstanceState.getParcelable("listener")
        }
    }

    /**
     * 保存接口
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("listener", convertListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        convertListener = null
    }

    companion object {

        fun init(): JDialogFragment {
            return JDialogFragment()
        }
    }
}
