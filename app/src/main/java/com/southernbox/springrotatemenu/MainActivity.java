package com.southernbox.springrotatemenu;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private View guillotineMenu;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        View contentHamburger = findViewById(R.id.content_hamburger);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);
        guillotineMenu.setVisibility(View.INVISIBLE);

        contentHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpringAnimation springAnimation;
                guillotineMenu.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.INVISIBLE);
                springAnimation = new SpringAnimation(guillotineMenu, SpringAnimation.ROTATION, 0);
//                springAnimation.getSpring().setStiffness(800.0f);
                springAnimation.getSpring().setDampingRatio(0.60f);
                springAnimation.start();
            }
        });

        guillotineMenu.findViewById(R.id.guillotine_hamburger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpringAnimation springAnimation = new SpringAnimation(guillotineMenu, SpringAnimation.ROTATION, -90);
//                springAnimation.getSpring().setStiffness(800.0f);
                springAnimation.getSpring().setDampingRatio(0.60f);
                springAnimation.start();
                springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        guillotineMenu.setVisibility(View.INVISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
