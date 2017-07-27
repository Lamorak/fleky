package cz.lamorak.fleky.service

import cz.lamorak.fleky.model.Memory
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by ondrej on 25.7.2017.
 */
class MockMemoriesService : MemoriesService {

    override fun savedMemories(): Observable<List<Memory>> {
        return Observable.just((1..5).map { generateMockMemory(it.toString()) })
                .delay(1, TimeUnit.SECONDS)
    }

    override fun memory(id: String): Observable<Memory> {
        return Observable.just(generateMockMemory(id))
    }

    fun generateMockMemory(id: String): Memory {
        return Memory(
                id = id,
                title = "Memory #$id",
                content = "Lorem ipsum dolor sit amet",
                photo = "",
                timestamp = System.currentTimeMillis()
        )
    }
}