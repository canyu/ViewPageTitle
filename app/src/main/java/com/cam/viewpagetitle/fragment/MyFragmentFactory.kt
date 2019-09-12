package com.cam.viewpagetitle.fragment

import android.os.Bundle
import com.cam.viewpagetitle.fragment.BaseFragment.Companion.ARGS_TITLE

/**
 * Created by yuCan on 2019/9/12
 */
class MyFragmentFactory {

    companion object {

        const val INDEX_MSG = 0
        const val INDEX_CONTACT = 1
        const val INDEX_FIND = 2
        const val INDEX_MY = 3
        val sTabTitles: Array<String> = arrayOf("消息", "联系人", "发现", "我")

        fun newInstance(index:Int): BaseFragment {
            val args = Bundle()
            args.putString(ARGS_TITLE, sTabTitles[index])
            val fragment :BaseFragment = when (index) {
                INDEX_MSG -> ChatFragment()

                INDEX_CONTACT -> ContactFragment()

                INDEX_FIND -> FindFragment()

                else -> ProfileFragment()
            }
            fragment.arguments = args
            return fragment
        }
    }
}