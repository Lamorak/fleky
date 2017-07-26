package cz.lamorak.krev.util

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View

/**
 * Created by ondrej on 17.7.2017.
 */

object DataBindingUtil {

    @JvmStatic @BindingConversion
    fun booleanToVisibility(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic @BindingAdapter("isRefreshing")
    fun displayHtml(refreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
        refreshLayout.isRefreshing = refreshing
    }
}