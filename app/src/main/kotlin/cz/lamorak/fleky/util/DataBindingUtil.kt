package cz.lamorak.krev.util

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import cz.lamorak.fleky.adapter.MemoriesAdapter
import cz.lamorak.fleky.model.Memory
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
        }
    }
}