package cz.lamorak.fleky

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.databinding.ActivityAddMemoryBinding
import cz.lamorak.fleky.dependency.DaggerAddMemoryComponent
import cz.lamorak.fleky.model.MemoryDescription
import cz.lamorak.fleky.presenter.AddMemoryPresenter
import cz.lamorak.fleky.view.AddMemoryViewIntent
import cz.lamorak.fleky.view.AddMemoryViewIntent.*
import cz.lamorak.fleky.view.AddMemoryViewState
import cz.lamorak.fleky.view.AddMemoryViewState.*
import io.reactivex.disposables.CompositeDisposable
import java.io.ByteArrayOutputStream
import javax.inject.Inject


/**
 * Created by ondrej on 26.7.2017.
 *
 */
class AddMemoryActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 7
    private val IMAGE = "image"

    private lateinit var disposables: CompositeDisposable
    private lateinit var binding: ActivityAddMemoryBinding
    @Inject lateinit var presenter: AddMemoryPresenter
    private lateinit var viewIntents: PublishRelay<AddMemoryViewIntent>
    private var imageBytes: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAddMemoryComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityAddMemoryBinding>(this, R.layout.activity_add_memory)
        viewIntents = PublishRelay.create()
        imageBytes = savedInstanceState?.getByteArray(IMAGE)
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
        disposables.addAll(
                binding.buttonSave.clicks()
                        .filter { imageBytes != null && checkDescription()}
                        .map { SaveMemory(imageBytes!!, memoryDescription()) }
                        .subscribe(viewIntents),

                viewIntents.subscribe(presenter.intent()),

                presenter.viewState()
                        .subscribe(this::displayViewState)
        )
        viewIntents.accept(CreateMemory(imageBytes))
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putByteArray(IMAGE, imageBytes)
        super.onSaveInstanceState(outState)
    }

    private fun displayViewState(viewState: AddMemoryViewState) {
        when (viewState) {
            is Capture -> startCamera()
            is Finished -> finish()
            else -> binding.viewState = viewState
        }
    }

    private fun checkDescription(): Boolean {
        return binding.title.text.isNotEmpty()
    }

    private fun memoryDescription(): MemoryDescription {
        return MemoryDescription(binding.title.text.toString(), binding.comment.text.toString(), 0)
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageBytes = convertBitmap(data.extras.get("data") as Bitmap)
        }
    }

    private fun convertBitmap(bitmap: Bitmap): ByteArray {
        val croppedBitmap = squareBitmap(bitmap)
        bitmap.recycle()
        val byteOutputStream = ByteArrayOutputStream()
        croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
        return byteOutputStream.toByteArray()
    }

    private fun squareBitmap(bitmap: Bitmap): Bitmap {
        val size = minOf(bitmap.width, bitmap.height)
        val x = (bitmap.width - size) / 2
        val y = (bitmap.height - size) / 2
        return Bitmap.createBitmap(bitmap, x, y, size, size)
    }
}