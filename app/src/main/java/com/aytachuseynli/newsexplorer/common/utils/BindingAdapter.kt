package com.aytachuseynli.newsexplorer.common.utils

import androidx.databinding.BindingAdapter
import com.aytachuseynli.newsexplorer.R
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

object BindingAdapter {

    @BindingAdapter("load_url")
    @JvmStatic
    fun loadUrl(view: ShapeableImageView, url: String?) {
        url?.let {
            Glide.with(view)
                .load(url)
                .placeholder(R.drawable.image_news1)
                .into(view)
        }
    }

}