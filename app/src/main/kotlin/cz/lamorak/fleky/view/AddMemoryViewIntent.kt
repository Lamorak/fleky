package cz.lamorak.fleky.view

import cz.lamorak.fleky.model.MemoryDescription

/**
 * Created by ondrej on 26.7.2017.
 */
sealed class AddMemoryViewIntent(val imageBytes: ByteArray? = null, val memory: MemoryDescription? = null) {

    class CreateMemory(imageBytes: ByteArray?) : AddMemoryViewIntent(imageBytes)
    class SaveMemory(imageBytes: ByteArray, memory: MemoryDescription): AddMemoryViewIntent(imageBytes, memory)

    override fun toString(): String {
        return when(this) {
            is CreateMemory -> "Create Memory intent"
            is SaveMemory -> "Save memory intent"
        }
    }
}