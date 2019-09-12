package com.cam.viewpagetitle.listener

import android.view.View

/**
 * Created by yuCan on 2019/9/12
 */
abstract class OnCustomClickListener@JvmOverloads constructor(private val timeOfSingleClick: Int = TIME_OF_SINGLE_CLICK) : View.OnClickListener {

    companion object{
        const val TIME_OF_SINGLE_CLICK = 1500
    }

    private var lastClickedViewId = -1
    private var lastClickTime:Long = 0

    final override fun onClick(v: View) {
        val timeMillis = System.currentTimeMillis()
        if(v.id != lastClickedViewId){
            lastClickedViewId = v.id
            lastClickTime = timeMillis
            onSingleClick(v)
            return
        }
        if(timeMillis - lastClickTime > timeOfSingleClick){
            lastClickTime = timeMillis
            onSingleClick(v)
        }else {
            onRepeatClick(v)
        }
    }

    abstract fun onSingleClick(v:View)

    open fun onRepeatClick(v:View){}
}
