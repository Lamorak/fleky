package cz.lamorak.fleky.service

import com.google.firebase.storage.UploadTask
import cz.lamorak.fleky.model.Memory
import cz.lamorak.fleky.model.MemoryDescription
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MockAddMemoryService : AddMemoryService {

    override fun uploadMemory(filename: String, imageBytes: ByteArray): Observable<UploadTask.TaskSnapshot> {
        return Observable.empty()
    }

    override fun saveMemory(id: String, memory: Memory): Completable {
        return Completable.complete()
    }
}