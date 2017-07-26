package cz.lamorak.krev.util

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import cz.lamorak.fleky.adapter.MemoriesAdapter
import cz.lamorak.fleky.model.Memory
import java.io.File
import java.text.SimpleDateFormat

/**
 * Created by ondrej on 17.7.2017.
 */

object DataBindingUtil {

    private val dateFormat = SimpleDateFormat.getDateTimeInstance()

    @JvmStatic @BindingConversion
    fun booleanToVisibility(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic @BindingAdapter("isRefreshing")
    fun setRefreshing(refreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
        refreshLayout.isRefreshing = refreshing
    }

    @JvmStatic @BindingAdapter("memories")
    fun memoriesAdapter(recyclerView: RecyclerView, memories: List<Memory>?) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = MemoriesAdapter(memories ?: emptyList())
    }

    @JvmStatic @BindingAdapter("timestamp")
    fun timestampToString(textView: TextView, timestamp: Long) {
        textView.text = dateFormat.format(timestamp)
    }

    @JvmStatic @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        if (url?.isNotEmpty() ?: false) {
            Picasso.with(imageView.context.applicationContext)
                    .load(url)
                    .into(imageView)
        } else {
            imageView.setImageDrawable(null)
        }
    }

    @JvmStatic @BindingAdapter("imageBytes")
    fun loadImage(imageView: ImageView, imageBytes: ByteArray?) {
        if (imageBytes != null) {
            val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageView.setImageBitmap(imageBitmap)
        } else {
            imageView.setImageDrawable(null)
        }
    }

    @JvmStatic @BindingAdapter("showButton")
    fun hideFloatingActionButton(floatingActionButton: FloatingActionButton, show: Boolean) {
        if (show) floatingActionButton.show() else floatingActionButton.hide()
    }
}