package cz.lamorak.fleky.dependency

import cz.lamorak.fleky.MainActivity
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by ondrej on 25.7.2017.
 */
@Singleton
@Component(modules = arrayOf(MainModule::class, RetrofitModule::class))
interface MainComponent : AndroidInjector<MainActivity>