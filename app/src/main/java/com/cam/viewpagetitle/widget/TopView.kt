package com.cam.viewpagetitle.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.cam.viewpagetitle.R
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Created by yuCan on 2019/9/11
 */
private const val TAG = "TopView"
class TopView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr){

    private var mScale:Float = 0f
    private val mTvTitle:TextView

    private val mSelectedColor:Int
    private val mUnselectedColor:Int
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

    private fun updateHeight():Float{
        (mScale <= 0f && measuredHeight > 0).let {
            mTvTitle.pivotX = mTvTitle.measuredWidth / 2f
            mTvTitle.pivotY = (measuredHeight - mTvTitle.measuredHeight) / 2f
            val heightScale = (measuredHeight - mTvTitle.measuredHeight) * 1f / mTvTitle.measuredHeight
            val widthScale = (measuredWidth - mTvTitle.measuredWidth) * 1f / mTvTitle.measuredWidth
            Log.d(TAG, "(updateHeight):heightScale: $heightScale, widthScale: $widthScale ")
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
        mTvTitle.setTextColor(evaluate(percent, mUnselectedColor, mSelectedColor))
    }

    /**
     * 计算不同进度值对应的颜色值，这个方法取自 ArgbEvaluator.java 类。
     * @param percentage 进度值，范围[0, 1]。
     * @param startValue 起始颜色值。
     * @param endValue 最终颜色值。
     * @return 返回与进度值相应的颜色值。
     */
    private fun evaluate(percentage: Float, startValue: Int, endValue: Int): Int {
        val startA = (startValue shr 24 and 0xff) / 255.0f
        var startR = (startValue shr 16 and 0xff) / 255.0f
        var startG = (startValue shr 8 and 0xff) / 255.0f
        var startB = (startValue and 0xff) / 255.0f

        val endA = (endValue shr 24 and 0xff) / 255.0f
        var endR = (endValue shr 16 and 0xff) / 255.0f
        var endG = (endValue shr 8 and 0xff) / 255.0f
        var endB = (endValue and 0xff) / 255.0f

        // convert from sRGB to linear
        startR = startR.toDouble().pow(2.2).toFloat()
        startG = startG.toDouble().pow(2.2).toFloat()
        startB = startB.toDouble().pow(2.2).toFloat()

        endR = endR.toDouble().pow(2.2).toFloat()
        endG = endG.toDouble().pow(2.2).toFloat()
        endB = endB.toDouble().pow(2.2).toFloat()

        // compute the interpolated color in linear space
        var a = startA + percentage * (endA - startA)
        var r = startR + percentage * (endR - startR)
        var g = startG + percentage * (endG - startG)
        var b = startB + percentage * (endB - startB)

        // convert back to sRGB in the [0..255] range
        a *= 255.0f
        r = r.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f
        g = g.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f
        b = b.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f

        return a.roundToInt() shl 24 or (r.roundToInt() shl 16) or (g.roundToInt() shl 8) or b.roundToInt()
    }
}
