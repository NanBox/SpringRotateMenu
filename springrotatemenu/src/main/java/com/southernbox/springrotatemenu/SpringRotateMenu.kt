package com.southernbox.springrotatemenu

import android.annotation.SuppressLint
import android.content.Context
import android.support.animation.SpringAnimation
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * Created by SouthernBox on 2017/6/19 0010.
 * 旋转菜单控件
 */

class SpringRotateMenu @JvmOverloads constructor(context: Context,
                                                 attrs: AttributeSet? = null,
                                                 defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val expandAnimation: SpringAnimation
    private val collapseAnimation: SpringAnimation

    private val screenWidth: Int
    private var listener: OnAnimationListener? = null

    companion object {
        private val ROTATE_EXPAND = 0
        private val ROTATE_COLLAPSE = -90
    }

    interface OnAnimationListener {

        /**
         * 开始展开
         */
        fun expandBegin()

        /**
         * 展开完毕
         */
        fun expandEnd()

        /**
         * 开始折叠
         */
        fun collapseBegin()

        /**
         * 折叠完毕
         */
        fun collapseEnd()

    }

    init {
        //默认为折叠并隐藏
        rotation = ROTATE_COLLAPSE.toFloat()
        visibility = View.INVISIBLE
        //展开动画
        expandAnimation = SpringAnimation(this, SpringAnimation.ROTATION, ROTATE_EXPAND.toFloat())
        expandAnimation.spring.dampingRatio = 0.60f
        //折叠动画
        collapseAnimation = SpringAnimation(this, SpringAnimation.ROTATION, ROTATE_COLLAPSE.toFloat())
        collapseAnimation.spring.dampingRatio = 0.60f
        //获取屏幕宽度
        screenWidth = context.resources.displayMetrics.widthPixels
    }

    /**
     * 设置展开按钮
     */
    fun setExpandButton(expandButton: View) {
        expandButton.setOnClickListener { expand() }
    }

    /**
     * 设置折叠按钮
     */
    fun setCollapseButton(collapseButton: View) {
        collapseButton.setOnClickListener { collapse() }
    }

    /**
     * 设置折叠监听器

     * @param listener 折叠监听器
     */
    fun setAnimationListener(listener: OnAnimationListener) {
        this.listener = listener
    }

    /**
     * 展开菜单
     */
    fun expand() {
        visibility = View.VISIBLE
        expandAnimation.start()
        if (listener != null) {
            listener!!.expandBegin()
            collapseAnimation.addEndListener { _, _, _, _ ->
                visibility = View.INVISIBLE
                listener!!.expandEnd()
            }
        }
    }

    /**
     * 折叠菜单
     */
    fun collapse() {
        collapseAnimation.start()
        if (listener != null) {
            listener!!.collapseBegin()
            collapseAnimation.addEndListener { _, _, _, _ ->
                listener!!.collapseEnd()
            }
        }
    }

    private var mDownX: Float = 0.toFloat()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (expandAnimation.isRunning || collapseAnimation.isRunning) {
            return super.onTouchEvent(event)
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                mDownX = event.rawX
            MotionEvent.ACTION_MOVE -> {
                //滑动距离
                val deltaX = event.rawX - mDownX
                //设置角度
                val rotation = deltaX / (screenWidth * 0.8f) * ROTATE_COLLAPSE
                when {
                    rotation in ROTATE_COLLAPSE..ROTATE_EXPAND -> setRotation(rotation)
                    rotation > ROTATE_EXPAND -> setRotation(ROTATE_EXPAND.toFloat())
                    rotation < ROTATE_COLLAPSE -> setRotation(ROTATE_COLLAPSE.toFloat())
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                if (rotation < ROTATE_COLLAPSE / 3) {
                    collapse()
                } else {
                    expand()
                }
        }
        return true
    }

}
