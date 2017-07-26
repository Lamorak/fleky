package cz.lamorak.fleky.view

import cz.lamorak.fleky.model.Memory

/**
 * Created by ondrej on 25.7.2017.
 *
 */
sealed class MemoriesViewState(
        val loading: Boolean = false,
        val memories: List<Memory>? = null,
        val error: Throwable? = null) {

    class Loading : MemoriesViewState(loading = true)
    class Content(memories: List<Memory>) : MemoriesViewState(memories = memories)
    class Error(error: Throwable) : MemoriesViewState(error = error)

    fun hasMemories(): Boolean {
        return memories != null && memories.isNotEmpty()
    }

    fun hasEmptyMemories(): Boolean {
        return memories != null && memories.isEmpty()
    }

    fun hasError(): Boolean {
        return error != null
    }

    fun errorMessage(): String {
        return error?.message ?: ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemoriesViewState) return false

        if (loading != other.loading) return false
        if (memories != other.memories) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = loading.hashCode()
        result = 31 * result + (memories?.hashCode() ?: 0)
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Content -> "List of Memories: ${memories?.size}"
            is Error -> "Error: ${error?.message}"
        }
    }
}

