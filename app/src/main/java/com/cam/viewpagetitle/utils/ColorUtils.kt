package com.cam.viewpagetitle.utils

import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Created by yuCan on 2019/9/12
 */
class ColorUtils{
    companion object{
        /**
         * 计算不同进度值对应的颜色值，这个方法取自 ArgbEvaluator.java 类。
         * @param percentage 进度值，范围[0, 1]。
         * @param startValue 起始颜色值。
         * @param endValue 最终颜色值。
         * @return 返回与进度值相应的颜色值。
         */
        fun evaluateColor(percentage: Float, startValue: Int, endValue: Int):Int{
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
}
