package cz.lamorak.fleky.util

import io.reactivex.observers.TestObserver

fun <T> TestObserver<T>.assertConnected() {
    this.assertSubscribed()
    this.assertNotComplete()
    this.assertNoErrors()
}