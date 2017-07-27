package cz.lamorak.fleky.service

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import cz.lamorak.fleky.model.Memory
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class AddMemoryServiceImpl @Inject constructor(private val storageReference: StorageReference,
                                               private val databaseReference: DatabaseReference) : AddMemoryService {

    override fun uploadMemory(filename: String, imageBytes: ByteArray): Observable<UploadTask.TaskSnapshot> {
        return Observable.create {
            storageReference.child(filename).putBytes(imageBytes).addOnProgressListener { taskSnapshot ->
                it.onNext(taskSnapshot)
            }.addOnCompleteListener { task ->
                it.onNext(task.result)
            }
        }
    }

    override fun saveMemory(id: String, memory: Memory): Completable {
        return Completable.create {
            databaseReference.child(id).setValue(memory) { error, _ ->
                if (error != null) it.onError(Throwable(error.message))
                it.onComplete()
            }
        }
    }
}