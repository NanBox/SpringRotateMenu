package com.southernbox.springrotatemenu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.southernbox.springrotatemenu.R;

/**
 * Created by SouthernBox on 2017/4/11 0011.
 * 史塔克家族
 */

public class StarkFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stark, container, false);
    }
}
