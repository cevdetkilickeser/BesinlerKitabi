package com.cevdetkilickeser.besinlerkitabi.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cevdetkilickeser.besinlerkitabi.R

fun ImageView.gorselIndir(url: String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun placeholderYap(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url: String?){
    view.gorselIndir(url, placeholderYap(view.context))
}
fun main() {
    val ornekDizi = arrayOf(1,3,5,7)
    for (numara in ornekDizi) { println(numara * 10) }
}
