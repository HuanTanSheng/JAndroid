package com.huantansheng.jandroid.dialog.jDialog

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*
import com.huantansheng.jandroid.R

abstract class BaseJDialogFragment : DialogFragment() {

    private var margin: Int = 0//左右边距
    private var width: Int = 0//宽度
    private var height: Int = 0//高度
    private var dimAmount = 0.5f//灰度深浅
    private var showBottom: Boolean = false//是否底部显示
    private var outCancel = true//是否点击外部取消
    @StyleRes
    private var animStyle: Int = 0
    @LayoutRes
    protected var layoutId: Int = 0

    abstract fun intLayoutId(): Int

    abstract fun convertView(holder: ViewHolder, dialogFragment: BaseJDialogFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.JDialog)
        layoutId = intLayoutId()

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN)
            width = savedInstanceState.getInt(WIDTH)
            height = savedInstanceState.getInt(HEIGHT)
            dimAmount = savedInstanceState.getFloat(DIM)
            showBottom = savedInstanceState.getBoolean(BOTTOM)
            outCancel = savedInstanceState.getBoolean(CANCEL)
            animStyle = savedInstanceState.getInt(ANIM)
            layoutId = savedInstanceState.getInt(LAYOUT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        convertView(ViewHolder.create(view), this)
        return view
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MARGIN, margin)
        outState.putInt(WIDTH, width)
        outState.putInt(HEIGHT, height)
        outState.putFloat(DIM, dimAmount)
        outState.putBoolean(BOTTOM, showBottom)
        outState.putBoolean(CANCEL, outCancel)
        outState.putInt(ANIM, animStyle)
        outState.putInt(LAYOUT, layoutId)
    }

    private fun initParams() {
        val window = dialog.window
        if (window != null) {
            val lp = window.attributes
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount
            //是否在底部显示
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM
                if (animStyle == 0) {
                    animStyle = R.style.JDialogDefaultAnimation
                }
            }

            //设置dialog宽度
            when (width) {
                0 -> lp.width = Utils.getScreenWidth(context!!) - 2 * Utils.dp2px(context!!, margin.toFloat())
                -1 -> lp.width = WindowManager.LayoutParams.WRAP_CONTENT
                else -> lp.width = Utils.dp2px(context!!, width.toFloat())
            }

            //设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                lp.height = Utils.dp2px(context!!, height.toFloat())
            }

            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle)
            window.attributes = lp
        }
        isCancelable = outCancel
    }

    fun setMargin(margin: Int): BaseJDialogFragment {
        this.margin = margin
        return this
    }

    fun setWidth(width: Int): BaseJDialogFragment {
        this.width = width
        return this
    }

    fun setHeight(height: Int): BaseJDialogFragment {
        this.height = height
        return this
    }

    fun setDimAmount(dimAmount: Float): BaseJDialogFragment {
        this.dimAmount = dimAmount
        return this
    }

    fun setShowBottom(showBottom: Boolean): BaseJDialogFragment {
        this.showBottom = showBottom
        return this
    }

    fun setOutCancel(outCancel: Boolean): BaseJDialogFragment {
        this.outCancel = outCancel
        return this
    }

    fun setAnimStyle(@StyleRes animStyle: Int): BaseJDialogFragment {
        this.animStyle = animStyle
        return this
    }

    fun show(manager: FragmentManager): BaseJDialogFragment {
        val ft = manager.beginTransaction()
        if (this.isAdded) {
            ft.remove(this).commit()
        }
        ft.add(this, System.currentTimeMillis().toString())
        ft.commitAllowingStateLoss()
        return this
    }

    companion object {
        private val MARGIN = "margin"
        private val WIDTH = "width"
        private val HEIGHT = "height"
        private val DIM = "dim_amount"
        private val BOTTOM = "show_bottom"
        private val CANCEL = "out_cancel"
        private val ANIM = "anim_style"
        private val LAYOUT = "layout_id"
    }
}
