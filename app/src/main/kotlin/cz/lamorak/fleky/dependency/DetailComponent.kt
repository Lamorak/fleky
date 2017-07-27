package cz.lamorak.fleky.dependency

import cz.lamorak.fleky.DetailActivity
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by ondrej on 27.7.2017.
 */
@Singleton
@Component(modules = arrayOf(DetailModule::class, FirebaseModule::class))
interface DetailComponent : AndroidInjector<DetailActivity>