package cz.lamorak.fleky.service

import com.google.firebase.storage.UploadTask
import cz.lamorak.fleky.model.Memory
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by ondrej on 26.7.2017.
 */
interface AddMemoryService {

    fun uploadMemory(filename: String, imageBytes: ByteArray): Observable<UploadTask.TaskSnapshot>
    fun saveMemory(id: String, memory: Memory): Completable
}