package cz.lamorak.fleky.service

import cz.lamorak.fleky.model.Memory
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by ondrej on 25.7.2017.
 */
class MockMemoriesService : MemoriesService {

    override fun savedMemories(): Observable<List<Memory>> {
        return Observable.just((1..5).map { generateMockMemory(it) })
                .delay(1, TimeUnit.SECONDS)
    }

    fun generateMockMemory(id: Int): Memory {
        return Memory(
                id = id.toLong(),
                title = "Memory #$id",
                content = "Lorem ipsum dolor sit amet",
                photo = "",
                thumbnail = "",
                timestamp = System.currentTimeMillis()
        )
    }
}