package com.southernbox.springrotatemenu.widget;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by SouthernBox on 2017/4/10 0010.
 * 旋转菜单控件
 */

public class SpringRotateMenu extends FrameLayout {

    private final static int ROTATE_EXPAND = 0;
    private final static int ROTATE_COLLAPSE = -90;

    private SpringAnimation expandAnimation;
    private SpringAnimation collapseAnimation;

    private OnAnimationListener listener;

    public interface OnAnimationListener {

        /**
         * 开始展开
         */
        void expandBegin();

        /**
         * 展开完毕
         */
        void expandEnd();

        /**
         * 开始折叠
         */
        void collapseBegin();

        /**
         * 折叠完毕
         */
        void collapseEnd();

    }

    public SpringRotateMenu(@NonNull Context context) {
        this(context, null);
    }

    public SpringRotateMenu(@NonNull Context context,
                            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringRotateMenu(@NonNull Context context,
                            @Nullable AttributeSet attrs,
                            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //默认为折叠并隐藏
        setRotation(ROTATE_COLLAPSE);
        setVisibility(INVISIBLE);
        //展开动画
        expandAnimation = new SpringAnimation(this, SpringAnimation.ROTATION, ROTATE_EXPAND);
        expandAnimation.getSpring().setDampingRatio(0.60f);
        //折叠动画
        collapseAnimation = new SpringAnimation(this, SpringAnimation.ROTATION, ROTATE_COLLAPSE);
        collapseAnimation.getSpring().setDampingRatio(0.60f);
    }

    /**
     * 设置展开按钮
     */
    public void setExpandButton(View expandButton) {
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand();
            }
        });
    }

    /**
     * 设置折叠按钮
     */
    public void setCollapseButton(View collapseButton) {
        collapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapse();
            }
        });
    }

    /**
     * 设置折叠监听器
     *
     * @param listener 折叠监听器
     */
    public void setAnimationListener(OnAnimationListener listener) {
        this.listener = listener;
    }

    /**
     * 展开菜单
     */
    public void expand() {
        setVisibility(VISIBLE);
        expandAnimation.start();
        if (listener != null) {
            listener.expandBegin();
            collapseAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation,
                                           boolean canceled,
                                           float value,
                                           float velocity) {
                    setVisibility(INVISIBLE);
                    listener.expandEnd();
                }
            });
        }
    }

    /**
     * 折叠菜单
     */
    public void collapse() {
        collapseAnimation.start();
        if (listener != null) {
            listener.collapseBegin();
            collapseAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation,
                                           boolean canceled,
                                           float value,
                                           float velocity) {
                    listener.collapseEnd();
                }
            });
        }
    }

    private float mDownX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (expandAnimation.isRunning() || collapseAnimation.isRunning()) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动距离
                float deltaX = event.getRawX() - mDownX;
                //设置角度
                float rotation = (deltaX / (getWidth() * 0.8f)) * ROTATE_COLLAPSE;
                if (rotation <= ROTATE_EXPAND && rotation >= ROTATE_COLLAPSE) {
                    setRotation(rotation);
                } else if (rotation > ROTATE_EXPAND) {
                    setRotation(ROTATE_EXPAND);
                } else if (rotation < ROTATE_COLLAPSE) {
                    setRotation(ROTATE_COLLAPSE);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getRotation() < ROTATE_COLLAPSE / 3) {
                    collapse();
                } else {
                    expand();
                }
                break;
        }
        return true;
    }

}
