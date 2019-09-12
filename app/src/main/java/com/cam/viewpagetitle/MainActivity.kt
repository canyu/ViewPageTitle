package com.cam.viewpagetitle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cam.viewpagetitle.fragment.*
import com.cam.viewpagetitle.fragment.MyFragmentFactory.Companion.INDEX_CONTACT
import com.cam.viewpagetitle.fragment.MyFragmentFactory.Companion.INDEX_FIND
import com.cam.viewpagetitle.fragment.MyFragmentFactory.Companion.INDEX_MSG
import com.cam.viewpagetitle.fragment.MyFragmentFactory.Companion.INDEX_MY
import com.cam.viewpagetitle.fragment.MyFragmentFactory.Companion.sTabTitles
import com.cam.viewpagetitle.listener.OnCustomClickListener
import com.cam.viewpagetitle.widget.TopView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val mTopViews = ArrayList<TopView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTopViews.add(tvWeiXin)
        mTopViews.add(tvContact)
        mTopViews.add(tvFind)
        mTopViews.add(tvMy)

        for(topView  in mTopViews){
            topView.setOnClickListener(onClick)
        }
        viewPager.offscreenPageLimit = sTabTitles.size - 1
        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            /**
             * @param position 滑动的时候，position总是代表左边的View， position+1总是代表右边的View
             * @param positionOffset 左边View位移的比例
             * @param positionOffsetPixels 左边View位移的像素
             */
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // 左边View进行动画
                mTopViews[position].setPercent(1 - positionOffset)
                // 如果positionOffset非0，那么就代表右边的View可见，也就说明需要对右边的View进行动画
                if (positionOffset > 0) {
                    mTopViews[position + 1].setPercent(positionOffset)
                }
            }
        })
    }

    private val onClick = object : OnCustomClickListener() {
        override fun onSingleClick(v: View) {
            val index:Int = when(v.id){
                R.id.tvWeiXin ->{
                    INDEX_MSG
                }
                R.id.tvContact ->{
                    INDEX_CONTACT
                }
                R.id.tvFind ->{
                    INDEX_FIND
                }
                else ->{
                    INDEX_MY
                }
            }
            viewPager.setCurrentItem(index, false)
            updateCurrentTab(index)
        }

        override fun onRepeatClick(v: View) {
            Toast.makeText(this@MainActivity, "点的人家有点痛，麻烦温柔一点", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCurrentTab(index: Int) {
        for (i in mTopViews.indices) {
            (index == i).let {
                mTopViews[i].setSelectedState(it)
            }
        }
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return sTabTitles.size
        }

        override fun getItem(position: Int): Fragment {
            return MyFragmentFactory.newInstance(position)
        }
    }
}
