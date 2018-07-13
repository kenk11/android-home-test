package vn.tiki.android.androidhometest.util

import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import vn.tiki.android.androidhometest.R

/**
 * Created by ken on 7/13/18
 */
@GlideModule
class ImageLoader : AppGlideModule() {
    companion object {
        fun loadImage(view: ImageView, url: String?, placeholder: Int = R.color.white) {
            GlideApp.with(view.context)
                .load(url)
                .placeholder(placeholder)
                .into(view)
        }
    }
}