package cz.lamorak.fleky.model

import java.io.Serializable

/**
 * Created by ondrej on 26.7.2017.
 */
data class MemoryDescription(
        val title: String,
        val comment: String,
        val timestamp: Long
) : Serializable