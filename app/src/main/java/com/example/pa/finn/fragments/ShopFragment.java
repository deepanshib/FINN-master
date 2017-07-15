package com.example.pa.finn.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.pa.finn.R;

/**
 * Created by root on 14/7/17.
 */

public class ShopFragment extends Fragment {
    WebView web;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        web=(WebView)view.findViewById(R.id.web);
        WebSettings webs =web.getSettings();
        webs.setJavaScriptEnabled(true);
        web.loadUrl("http://www.netmeds.com/");
        web.setWebViewClient(new WebViewClient());
        return view;
    }
}
