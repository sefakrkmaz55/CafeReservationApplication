package com.bitirme.rezervasyonuygulamasi.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bitirme.rezervasyonuygulamasi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.getUrl(url:String?){
    val option=RequestOptions().error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(option).load(url).into(this)
}

@BindingAdapter("imageUrl")
fun downloadImage(imageView: ImageView,imageUrl: String?){
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }
}