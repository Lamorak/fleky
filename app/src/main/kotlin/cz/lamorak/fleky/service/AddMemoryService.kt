package cz.lamorak.fleky.service

import cz.lamorak.fleky.model.MemoryDescription
import io.reactivex.Completable

/**
 * Created by ondrej on 26.7.2017.
 */
interface AddMemoryService {

    fun upload(file: ByteArray?): Completable
    fun saveMemory(memory: MemoryDescription?): Completable
}