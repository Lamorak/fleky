package cz.lamorak.fleky.util

import io.reactivex.Observable
import java.util.logging.Logger

/**
 * Created by ondrej on 25.7.2017.
 */
object Monitor {

    fun log(tag: String, message: String) {
        Logger.getLogger(tag).info(message)
    }

    interface Loggable {
        fun log(message: String) = log(javaClass.simpleName, message)
    }
}

fun <T> Observable<T>.withLog() : Observable<T> {
    return doOnNext { Monitor.log(javaClass.simpleName, it.toString()) }
}