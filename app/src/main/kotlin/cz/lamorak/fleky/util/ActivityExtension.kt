package cz.lamorak.fleky.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Created by ondrej on 26.7.2017.
 */
fun Activity.start(clazz: Class<out Activity>, vararg params: Pair<String, String>) {
    val intent = Intent(this, clazz)
    for ((key, value) in params) {
        intent.putExtra(key, value)
    }
    startActivity(intent)
}