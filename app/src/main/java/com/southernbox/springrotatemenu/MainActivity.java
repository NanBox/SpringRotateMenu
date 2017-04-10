package com.southernbox.springrotatemenu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        View contentHamburger = findViewById(R.id.content_hamburger);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
        }

        SpringRotateMenu springRotateMenu = (SpringRotateMenu) LayoutInflater.from(this).inflate(R.layout.guillotine, root, false);
        root.addView(springRotateMenu);
        springRotateMenu.setExpandButton(contentHamburger);
        springRotateMenu.setCollapseButton(springRotateMenu.findViewById(R.id.guillotine_hamburger));
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
    }

}
