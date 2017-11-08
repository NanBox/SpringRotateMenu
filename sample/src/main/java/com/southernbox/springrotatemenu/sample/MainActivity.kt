package com.southernbox.springrotatemenu.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.southernbox.springrotatemenu.SpringRotateMenu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu.*

/**
 * Created by SouthernBox on 2017/6/19 0010.
 * 主页面
 */

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var starkFragment: Fragment
    private lateinit var lannisterFragment: Fragment
    private lateinit var baratheonFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initMenu()
        initFragment()
        initListener()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = null
        }
    }

    private fun initMenu() {
        root.addView(LayoutInflater.from(this).inflate(R.layout.menu, root, false))
        spring_rotate_menu.setExpandButton(iv_menu)
        spring_rotate_menu.setCollapseButton(iv_rotate_menu)
        spring_rotate_menu.setAnimationListener(object : SpringRotateMenu.OnAnimationListener {

            override fun expandBegin() {
                toolbar.visibility = View.INVISIBLE
            }

            override fun expandEnd() {

            }

            override fun collapseBegin() {

            }

            override fun collapseEnd() {
                toolbar.visibility = View.VISIBLE
            }
        })
    }

    private fun initFragment() {
        starkFragment = MainFragment().newInstance(R.layout.fragment_stark)
        lannisterFragment = MainFragment().newInstance(R.layout.fragment_lannister)
        baratheonFragment = MainFragment().newInstance(R.layout.fragment_baratheon)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.fl_content, starkFragment)
                .commit()
    }

    private fun initListener() {
        tv_stark.setOnClickListener(this)
        tv_lannister.setOnClickListener(this)
        tv_baratheon.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        tv_stark.setTextColor(Color.WHITE)
        tv_lannister.setTextColor(Color.WHITE)
        tv_baratheon.setTextColor(Color.WHITE)
        when (view.id) {
            R.id.tv_stark -> {
                tv_stark.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_content, starkFragment)
                        .commit()
                tv_title.setText(R.string.stark)
            }
            R.id.tv_lannister -> {
                tv_lannister.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_content, lannisterFragment)
                        .commit()
                tv_title.setText(R.string.lannister)
            }
            R.id.tv_baratheon -> {
                tv_baratheon.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_content, baratheonFragment)
                        .commit()
                tv_title.setText(R.string.baratheon)
            }
        }
        spring_rotate_menu.collapse()
    }
}
