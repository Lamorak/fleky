package cz.lamorak.fleky.dependency

import android.provider.Settings.Secure
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ondrej on 26.7.2017.
 *
 */
@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideDatabaseReference(userId: String): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(userId).child("memories")
    }

    @Provides
    @Singleton
    fun provideStorageReference(userId: String): StorageReference {
        return FirebaseStorage.getInstance().getReference().child(userId)
    }

    @Provides
    @Singleton
    fun provideUserId(): String {
        val contentResolver = FirebaseApp.getInstance()?.applicationContext?.contentResolver
        return Secure.getString(contentResolver, Secure.ANDROID_ID)
    }
}