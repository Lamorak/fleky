package cz.lamorak.fleky.service

import cz.lamorak.fleky.model.MemoryDescription
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class MockAddMemoryService : AddMemoryService {

    override fun upload(file: ByteArray?): Completable {
        return Completable.complete()
                .delay(2, TimeUnit.SECONDS)
    }

    override fun saveMemory(memory: MemoryDescription?): Completable {
        return Completable.complete()
                .delay(2, TimeUnit.SECONDS)
    }
}