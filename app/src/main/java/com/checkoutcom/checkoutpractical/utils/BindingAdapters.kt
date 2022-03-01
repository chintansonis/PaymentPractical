package com.checkoutcom.checkoutpractical.utils

import android.webkit.WebView
import androidx.databinding.BindingAdapter


@BindingAdapter("url")
fun WebView.loadRepoUrl(url: String) {
    run { loadUrl(url) }
}