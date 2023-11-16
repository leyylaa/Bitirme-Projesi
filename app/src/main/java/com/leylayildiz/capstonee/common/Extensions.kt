package com.leylayildiz.capstonee.common

import android.view.View
import android.graphics.Paint
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility=View.GONE
}
fun TextView.setStrikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).into(this)

}