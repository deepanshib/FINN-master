package com.example.pa.finn.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pa.finn.EditTaskActivity;
import com.example.pa.finn.MainActivity;
import com.example.pa.finn.R;
import com.tmall.ultraviewpager.UltraViewPager;

/**
 * Created by root on 14/7/17.
 */

public class HomeFragment extends Fragment {
    LinearLayout ll1, ll2, ll3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ll1 = (LinearLayout) view.findViewById(R.id.llTask1);
        ll2 = (LinearLayout) view.findViewById(R.id.llTask2);
        ll3 = (LinearLayout) view.findViewById(R.id.llTask3);

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditTaskActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditTaskActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditTaskActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
            }
        });

        return view;
    }
}
