package com.cam.viewpagetitle.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec.UNSPECIFIED
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.cam.viewpagetitle.R
import com.cam.viewpagetitle.utils.ColorUtils

/**
 * Created by yuCan on 2019/9/11
 */
private const val TAG = "TopView"
class TopView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr){

    private var mScale:Float = 0f
    private val mTvTitle:TextView

    private val mSelectedColor:Int
    private val mUnselectedColor:Int

    private var mMeasureTitleWidth:Int = 0

    init{
        val v: View = LayoutInflater.from(context).inflate(R.layout.view_top_view, this)
        mTvTitle = v.findViewById(R.id.tvTitle)
        val a = context.obtainStyledAttributes(attrs, R.styleable.TopView)
        val title = a.getString(R.styleable.TopView_topTitle)
        mSelectedColor = a.getColor(R.styleable.TopView_topSelectedColor, 0x333333)
        mUnselectedColor = a.getColor(R.styleable.TopView_topUnselectedColor, 0x333333)
        mTvTitle.text = title
        a.recycle()
    }

    fun getMeasureTitleWidth():Int{
        if(mMeasureTitleWidth != 0){
            return mMeasureTitleWidth
        }
        mTvTitle.measure(UNSPECIFIED,UNSPECIFIED)
        mMeasureTitleWidth = mTvTitle.measuredWidth
        return mMeasureTitleWidth
    }

    fun updateMeasureWidth(width:Int){
        mMeasureTitleWidth = width
    }

    private fun updateHeight():Float{
        (mScale <= 0f && measuredHeight > 0).let {
            var measureTitleWidth = mTvTitle.measuredWidth
            mTvTitle.pivotX = measureTitleWidth / 2f
            (mMeasureTitleWidth != 0).let{measureTitleWidth = mMeasureTitleWidth}
            mTvTitle.pivotY = (measuredHeight - mTvTitle.measuredHeight) / 2f
            val heightScale = (measuredHeight - mTvTitle.measuredHeight) * 1f / mTvTitle.measuredHeight
            val widthScale = (measuredWidth - measureTitleWidth) * 1f / measureTitleWidth
            Log.d(TAG, "(updateHeight):heightScale: $heightScale, widthScale: $widthScale ," +
                    "mTvTitle.measuredHeight:${mTvTitle.measuredHeight},mTvTitle.measuredWidth: ${mTvTitle.measuredWidth}," +
                    " mMeasureTitleWidth:$mMeasureTitleWidth ")
            mScale = heightScale.coerceAtMost(widthScale)
        }
        return mScale
    }

    fun setSelectedState(isSelected:Boolean){
        setPercent(if(isSelected) 1f else 0f)
    }
    fun setPercent(percent:Float){
        if(mScale <= 0f){
            updateHeight()
        }
        if(mScale > 0f && percent >= 0){
            mTvTitle.scaleX = 1 + percent * mScale
            mTvTitle.scaleY = 1 + percent * mScale
        }
        mTvTitle.setTextColor(ColorUtils.evaluateColor(percent, mUnselectedColor, mSelectedColor))
    }
}
