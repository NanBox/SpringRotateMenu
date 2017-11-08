package com.southernbox.springrotatemenu.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SouthernBox on 2017/6/19 0011.
 * 家族 Fragment
 */

class MainFragment : Fragment() {

    fun newInstance(id: Int): Fragment {
        val fragment = MainFragment()
        val bundle = Bundle()
        bundle.putInt("layoutId", id)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val layoutId = arguments.getInt("layoutId")
        return inflater.inflate(layoutId, container, false)
    }
}
