[![](https://travis-ci.org/SouthernBox/SpringRotateMenu.svg?branch=master)](https://travis-ci.org/SouthernBox/SpringRotateMenu)
[![](https://api.bintray.com/packages/southernbox/maven/SpringRotateMenu/images/download.svg)](https://bintray.com/southernbox/maven/SpringRotateMenu/_latestVersion)
[![](https://img.shields.io/badge/API-16+-green.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://badge.juejin.im/entry/58edcf2ba0bb9f006a4da090/likes.svg?style=flat)](https://juejin.im/post/58edcec344d904005774e6cf)

# SpringRotateMenu

A rotating menu with SpringAnimation.

![](http://upload-images.jianshu.io/upload_images/1763614-6902677cf6f2e4c8.gif?imageMogr2/auto-orient/strip)

# Usage

**Add the dependencies to your gradle file:**

```javascript
dependencies {
	compile 'com.southernbox:SpringRotateMenu:1.0.0'
}
```
**Use SpringRotateMenu in your layout file:**

```xml
<com.southernbox.springrotatemenu.SpringRotateMenu
    android:background="@color/colorPrimary"
    android:id="@+id/spring_rotate_menu"
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:transformPivotX="28dp"
    android:transformPivotY="28dp">

    <android.support.v7.widget.Toolbar
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        app:contentInsetStart="0dp">

        <ImageView
            android:id="@+id/iv_rotate_menu"
            android:layout_height="@dimen/toolbar_height"
            android:layout_width="@dimen/toolbar_height"
            android:rotation="90"
            android:scaleType="center"
            android:src="@drawable/ic_menu" />

    </android.support.v7.widget.Toolbar>

    <!-- layout -->

</com.southernbox.springrotatemenu.SpringRotateMenu>
```

**Setting button and listener:**

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