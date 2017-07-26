package cz.lamorak.fleky

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.databinding.ActivityMainBinding
import cz.lamorak.fleky.dependency.DaggerMainComponent
import cz.lamorak.fleky.presenter.MemoriesPresenter
import cz.lamorak.fleky.util.start
import cz.lamorak.fleky.view.MemoriesViewIntent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by ondrej on 25.7.2017.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var disposables: CompositeDisposable
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var presenter: MemoriesPresenter
    private lateinit var viewIntents: PublishRelay<MemoriesViewIntent>

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewIntents = PublishRelay.create()
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
        disposables.addAll(
                binding.buttonAddNew.clicks()
                        .subscribe { addNewMemory() },

                binding.swipeRefresh.refreshes()
                        .map { MemoriesViewIntent.Load() }
                        .subscribe(viewIntents),

                viewIntents.subscribe(presenter.intent()),

                presenter.viewState()
                        .subscribe(binding::setViewState)
        )
        viewIntents.accept(MemoriesViewIntent.Load())
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

    private fun addNewMemory() {
        start(AddMemoryActivity::class.java)
    }
}