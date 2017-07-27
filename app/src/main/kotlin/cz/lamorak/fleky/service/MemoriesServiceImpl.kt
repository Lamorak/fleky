package cz.lamorak.fleky.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import cz.lamorak.fleky.model.Memory
import io.reactivex.Observable
import javax.inject.Inject

class MemoriesServiceImpl @Inject constructor(private val databaseReference: DatabaseReference) : MemoriesService {

    override fun savedMemories(): Observable<List<Memory>> {
        return Observable.create<List<Memory>> {
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val memories = dataSnapshot.children
                            .map { it.getValue(Memory::class.java)!! }
                    it.onNext(memories)
                    it.onComplete()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    it.onError(Throwable(databaseError.message))
                }
            })
        }
    }
}