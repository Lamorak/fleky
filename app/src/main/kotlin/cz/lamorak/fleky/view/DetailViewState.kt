package cz.lamorak.fleky.view

import cz.lamorak.fleky.model.Memory

/**
 * Created by ondrej on 27.7.2017.
 */
sealed class DetailViewState(
        val loading: Boolean = false,
        val memory: Memory? = null,
        val error: Throwable? = null) {

    class Loading : DetailViewState(loading = true)
    class Content(memory: Memory) : DetailViewState(memory = memory)
    class Error(error: Throwable) : DetailViewState(error = error)

    fun errorMessage(): String {
        return error?.message ?: ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DetailViewState) return false

        if (loading != other.loading) return false
        if (memory != other.memory) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = loading.hashCode()
        result = 31 * result + (memory?.hashCode() ?: 0)
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Content -> "Memory: $memory"
            is Error -> "Error: ${error?.message}"
        }
    }
}