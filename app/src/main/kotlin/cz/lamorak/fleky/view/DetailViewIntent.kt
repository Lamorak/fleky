package cz.lamorak.fleky.view

/**
 * Created by ondrej on 27.7.2017.
 */
sealed class DetailViewIntent(val id: String) {

    class Load(id: String) : DetailViewIntent(id = id)
}