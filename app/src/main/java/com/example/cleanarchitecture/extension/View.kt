package com.example.cleanarchitecture.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import java.io.File

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.setVisible(visible: Boolean, invisible: Boolean? = false) {
    visibility = when {
        visible -> View.VISIBLE
        invisible == true -> View.INVISIBLE
        else -> View.GONE
    }
}

private const val TAG = "View"
fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun View.hideKeyboard() {
    val inputMethod: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethod.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val inputMethod: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethod.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

inline fun View.afterMeasured(crossinline f: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

@BindingAdapter(value = ["singleClick", "hiddenKeyboard"], requireAll = false)
fun View.singleClickListener(singleClick: (() -> Unit)? = null, hiddenKeyboard: Boolean? = false) {
    setOnClickListener {
        singleClick?.invoke()
        isClickable = false
        when (hiddenKeyboard) {
            true -> context.showSoftKeyboard(false)
        }

        postDelayed({
            isClickable = true
        }, 300L)
    }
}

@BindingAdapter("circleUrl")
fun ImageView.circleUrl(url: String?) = url?.let {
   // Uri myUri = Uri.parse("http://stackoverflow.com")
    /*
    Glide.with(context)
        .load(File(url.path))
        .apply(RequestOptions.circleCropTransform())
        .listener(object :RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d(TAG, "onLoadFaileded: "+e?.message)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return true
            }

        })
        .into(this)*/
    Picasso.get().load(it).into(this)

}