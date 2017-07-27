package cz.lamorak.fleky.model

/**
 * Created by ondrej on 25.7.2017.
 */
data class Memory(
        val id: String = "",
        val title: String = "",
        val content: String = "",
        val photo: String = "",
//        val thumbnail: String,
        val timestamp: Long = 0
)