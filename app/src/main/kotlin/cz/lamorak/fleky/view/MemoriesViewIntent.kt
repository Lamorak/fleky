package cz.lamorak.fleky.view

/**
 * Created by ondrej on 26.7.2017.
 *
 */
sealed class MemoriesViewIntent(val loadMemories: Boolean) {

    class Load : MemoriesViewIntent(loadMemories = true)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemoriesViewIntent) return false

        if (loadMemories != other.loadMemories) return false

        return true
    }

    override fun hashCode(): Int {
        return loadMemories.hashCode()
    }

    override fun toString(): String {
        return when(this) {
            is Load -> "Load Intent"
        }
    }
}