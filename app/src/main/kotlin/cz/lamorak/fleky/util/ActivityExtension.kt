package cz.lamorak.fleky.util

import android.app.Activity
import android.content.Intent

/**
 * Created by ondrej on 26.7.2017.
 */
fun Activity.start(clazz: Class<out Activity>, vararg params: Pair<String, Any>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}