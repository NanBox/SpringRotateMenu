package com.southernbox.springrotatemenu.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.southernbox.springrotatemenu.R;
import com.southernbox.springrotatemenu.fragment.BaratheonFragment;
import com.southernbox.springrotatemenu.fragment.LannisterFragment;
import com.southernbox.springrotatemenu.fragment.StarkFragment;
import com.southernbox.springrotatemenu.widget.SpringRotateMenu;

/**
 * Created by SouthernBox on 2017/4/10 0010.
 * 主页面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvStark;
    private TextView tvLannister;
    private TextView tvBaratheon;
    private SpringRotateMenu springRotateMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
        }

        springRotateMenu = (SpringRotateMenu) LayoutInflater.from(this)
                .inflate(R.layout.menu, root, false);
        root.addView(springRotateMenu);
        springRotateMenu.setExpandButton(findViewById(R.id.iv_menu));
        springRotateMenu.setCollapseButton(springRotateMenu.findViewById(R.id.iv_menu));
        springRotateMenu.setAnimationListener(new SpringRotateMenu.OnAnimationListener() {

            @Override
            public void expandBegin() {
                toolbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void expandEnd() {

            }

            @Override
            public void collapseBegin() {

            }

            @Override
            public void collapseEnd() {
                toolbar.setVisibility(View.VISIBLE);
            }
        });

        //给菜单添加点击事件
        tvStark = (TextView) springRotateMenu.findViewById(R.id.tv_stark);
        tvStark.setOnClickListener(this);
        tvLannister = (TextView) springRotateMenu.findViewById(R.id.tv_lannister);
        tvLannister.setOnClickListener(this);
        tvBaratheon = (TextView) springRotateMenu.findViewById(R.id.tv_baratheon);
        tvBaratheon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        tvStark.setTextColor(Color.WHITE);
        tvLannister.setTextColor(Color.WHITE);
        tvBaratheon.setTextColor(Color.WHITE);
        switch (view.getId()) {
            case R.id.tv_stark:
                tvStark.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new StarkFragment())
                        .commit();
                tvTitle.setText(R.string.stark);
                break;
            case R.id.tv_lannister:
                tvLannister.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new LannisterFragment())
                        .commit();
                tvTitle.setText(R.string.lannister);
                break;
            case R.id.tv_baratheon:
                tvBaratheon.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new BaratheonFragment())
                        .commit();
                tvTitle.setText(R.string.baratheon);
                break;
        }
        springRotateMenu.collapse();
    }
}
