package cz.lamorak.fleky.service

import cz.lamorak.fleky.model.Memory
import io.reactivex.Observable

/**
 * Created by ondrej on 25.7.2017.
 */
interface MemoriesService {

    fun savedMemories() : Observable<List<Memory>>
    fun memory(id: String) : Observable<Memory>
}