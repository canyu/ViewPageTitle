package com.cam.viewpagetitle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.cam.viewpagetitle.R


/**
 * Created by yuCan on 2019/9/11
 */
abstract class BaseFragment : Fragment() {
    private var mTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            mTitle = arguments.getString(ARGS_TITLE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.common_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleView = view.findViewById<TextView>(R.id.fragment_tv)
        titleView.text = mTitle
    }

    abstract fun getLayoutRes():Int

    companion object{
        const val ARGS_TITLE = "title"
    }
}
