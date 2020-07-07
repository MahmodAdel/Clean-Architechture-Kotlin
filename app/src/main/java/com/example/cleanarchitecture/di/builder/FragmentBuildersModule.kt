package com.example.cleanarchitecture.di.builder

import com.example.cleanarchitecture.MainActivity
import com.example.cleanarchitecture.ui.CarFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun carFragment(): CarFragment


}
