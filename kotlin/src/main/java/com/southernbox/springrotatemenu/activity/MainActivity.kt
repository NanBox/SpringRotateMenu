package com.southernbox.springrotatemenu.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.southernbox.springrotatemenu.R
import com.southernbox.springrotatemenu.fragment.BaratheonFragment
import com.southernbox.springrotatemenu.fragment.LannisterFragment
import com.southernbox.springrotatemenu.fragment.StarkFragment
import com.southernbox.springrotatemenu.widget.SpringRotateMenu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu.*

/**
 * Created by SouthernBox on 2017/6/19 0010.
 * 主页面
 */

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = null
        }

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

        //给菜单添加点击事件
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
                        .replace(R.id.fragment, StarkFragment())
                        .commit()
                tv_title.setText(R.string.stark)
            }
            R.id.tv_lannister -> {
                tv_lannister.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment, LannisterFragment())
                        .commit()
                tv_title.setText(R.string.lannister)
            }
            R.id.tv_baratheon -> {
                tv_baratheon.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment, BaratheonFragment())
                        .commit()
                tv_title.setText(R.string.baratheon)
            }
        }
        spring_rotate_menu.collapse()
    }
}
