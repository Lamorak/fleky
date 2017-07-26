package cz.lamorak.fleky.view

import java.util.*

/**
 * Created by ondrej on 26.7.2017.
 */
sealed class AddMemoryViewState(val imageBytes: ByteArray? = null,
                                val uploading: Boolean = false,
                                val finished: Boolean = false) {

    class Capture : AddMemoryViewState()
    class AddDesciption(imageBytes: ByteArray) : AddMemoryViewState(imageBytes)
    class Uploading : AddMemoryViewState(uploading = true)
    class Finished : AddMemoryViewState()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AddMemoryViewState) return false

        if (!Arrays.equals(imageBytes, other.imageBytes)) return false
        if (uploading != other.uploading) return false
        if (finished != other.finished) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageBytes?.let { Arrays.hashCode(it) } ?: 0
        result = 31 * result + uploading.hashCode()
        result = 31 * result + finished.hashCode()
        return result
    }

    override fun toString(): String {
        return when (this) {
            is Capture -> "Capture photo"
            is AddDesciption -> "Add memory description"
            is Uploading -> "Uploading memory"
            is Finished -> "Memory uploaded"
        }
    }
}