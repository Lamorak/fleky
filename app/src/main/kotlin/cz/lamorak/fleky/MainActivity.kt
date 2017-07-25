package cz.lamorak.fleky

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import cz.lamorak.fleky.databinding.ActivityMainBinding
import cz.lamorak.fleky.dependency.DaggerMainComponent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ondrej on 25.7.2017.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var disposables: CompositeDisposable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
        disposables.add(
                binding.buttonAddNew.clicks()
                        .subscribe { onButtonClicked() }
        )
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

    fun onButtonClicked() {
        Snackbar.make(binding.root, "Click", Snackbar.LENGTH_SHORT).show()
    }
}