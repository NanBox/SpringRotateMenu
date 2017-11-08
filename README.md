[![](https://travis-ci.org/SouthernBox/SpringRotateMenu.svg?branch=master)](https://travis-ci.org/SouthernBox/SpringRotateMenu)
[![](https://api.bintray.com/packages/southernbox/maven/SpringRotateMenu/images/download.svg)](https://bintray.com/southernbox/maven/SpringRotateMenu/_latestVersion)
[![](https://img.shields.io/badge/API-16+-green.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://badge.juejin.im/entry/58edcf2ba0bb9f006a4da090/likes.svg?style=flat)](https://juejin.im/post/58edcec344d904005774e6cf)

侧滑菜单我们见的太多了，有没有想过有别的方式弹出菜单？  
比如，让 Toolbar 变成菜单？

我也不知道怎么描述这个效果了，直接放效果图吧：

![](http://upload-images.jianshu.io/upload_images/1763614-6902677cf6f2e4c8.gif?imageMogr2/auto-orient/strip)

炸不炸！

其实实现起来超简单。

# 思路

看上去好像 Toolbar 变成了菜单，但大家也能猜到，这里面的旋转菜单其实和 Toolbar 是两个控件，左上角的菜单按钮也是也是两个按钮，只不过在同样的位置放了同样的图片。

所以我自定义了一个旋转控件 SpringRotateMenu，继承 FrameLayout，在这里面实现旋转动画及手势操作。

# 旋转动画

Gif 图可能不明显，菜单展开和收起的时候是会抖一下的，有一种「DUANG」的感觉。是不是有种弹簧的感觉？没错，我用的就是新出的弹簧动画（SpringAnimation）。

关于 SpringAnimation，我之前的这篇会有更详细的介绍：

[实现一个带下拉弹簧动画的 ScrollView](https://github.com/SouthernBox/SpringScrollView)

SpringAnimation 支持平移、缩放、旋转等效果，这次我们用到的是它的旋转效果。

我们先定义展开和收起状态的两个角度：

```java

private final static int ROTATE_EXPAND = 0;
private final static int ROTATE_COLLAPSE = -90;
    
```

然后这样来获取旋转弹簧动画：

```java

expandAnimation = new SpringAnimation(this, SpringAnimation.ROTATION, ROTATE_EXPAND);
collapseAnimation = new SpringAnimation(this, SpringAnimation.ROTATION, ROTATE_COLLAPSE);

```

需要注意的是第三个参数。在平移动画里面，第三个参数是偏移量，而在旋转动画里面代表的是度数。在这里我定义了展开动画（旋转到0°）及收起动画（旋转到 -90°）。

然后提供两个方法来设置展开和收起的按钮：

```java

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

```

展开按钮就是 Toolbar 上的按钮，收起按钮则是菜单上的按钮。

展开、收起的方法也很简单：

```java

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

```

其实就是让对应的动画执行，菜单在开始展开的时候显示，在完全收起的时候隐藏。至于这里的 listener 是我加的一个动画监听器，监听两个动画的开始和结束，供外部使用。

# 手势操作

手势操作就是重写 onTouchEvent，代码如下：

```java

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
            float rotation = (deltaX / (screenWidth * 0.8f)) * ROTATE_COLLAPSE;
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

```

核心就是将手指的横向滑动距离转换为旋转角度。我的计算方法是，菜单控件的旋转角度，等于横向滑动距离占屏幕宽度的比例，乘以 -90°。至于为什么宽度要乘以 0.8，我是为了让手指在屏幕上滑过 80% 的宽度，就可以将菜单完全收起。

还有就是手指抬起时的处理。我觉得在用户向右滑动菜单时，大部分情况下是希望将菜单收起的，应该让它更容易收起。所以我的做法是，当手指抬起时，菜单竖直的角度超过 30°，就让它执行收起的动画，否则执行展开的动画。

# 使用

## 布局

使用 SpringRotateMenu 作为旋转菜单的根布局，并设置控件的旋转中心点。默认的 Toolbar 高度为 56dp，如果菜单按钮居中显示的话，可以使用：

```

android:transformPivotX="28dp"
android:transformPivotY="28dp"

```


然后用 FrameLayout 将它覆盖在 Toolbar 上面。

建议让菜单布局的背景颜色和 Toolbar 的颜色一致，并使用同一个菜单图标，菜单图标里面加一个参数：

```

android:rotation="90"

```

让图标旋转九十度。

## 代码

在代码里面找到我们的 SpringRotateMenu，然后简单的设置一下，比如这样：

```java

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

```

这样就完成啦，妥妥的。