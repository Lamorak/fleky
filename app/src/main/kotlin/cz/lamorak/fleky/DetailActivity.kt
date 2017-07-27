package cz.lamorak.fleky

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.databinding.ActivityDetailBinding
import cz.lamorak.fleky.dependency.DaggerDetailComponent
import cz.lamorak.fleky.model.Memory
import cz.lamorak.fleky.presenter.DetailPresenter
import cz.lamorak.fleky.view.DetailViewIntent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import android.content.Intent



/**
 * Created by ondrej on 27.7.2017.
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var disposables: CompositeDisposable
    private lateinit var binding: ActivityDetailBinding
    @Inject lateinit var presenter: DetailPresenter
    private lateinit var viewIntents: PublishRelay<DetailViewIntent>
    private lateinit var memoryId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerDetailComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        viewIntents = PublishRelay.create()
        memoryId = intent.extras.getString("memoryId")
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
        disposables.addAll(
                binding.buttonShare.clicks()
                        .map { binding.viewState.memory }
                        .subscribe(this::shareMemory),

                viewIntents.subscribe(presenter.intent()),

                presenter.viewState()
                        .subscribe(binding::setViewState)
        )
        viewIntents.accept(DetailViewIntent.Load(memoryId))
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

    private fun shareMemory(memory: Memory?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, "${memory?.title} ${memory?.photo}")
        shareIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareIntent, resources.getString(R.string.share_dialog_title)))
    }
}
