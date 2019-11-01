package com.app.adapter;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.webkit.WebView;

/*
 * Created by Yash on 14/6/18.
 */

public class WebDataBindingAdapter
{
    @BindingAdapter({"app:loadData"})
    public static void loadWebData(WebView webView,String htmlTxtParams)
    {
        if (!TextUtils.isEmpty(htmlTxtParams))
            webView.loadData(htmlTxtParams,"text/html","UTF-8");
    }
}
