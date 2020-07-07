package com.example.cleanarchitecture.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchitecture.ViewModelProviderFactory
import com.example.cleanarchitecture.di.annotation.ViewModelKey
import com.example.cleanarchitecture.ui.CarViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(providerFactory: ViewModelProviderFactory): ViewModelProvider.Factory



    @Binds
    @IntoMap
    @ViewModelKey(CarViewModel::class)
    abstract fun bindRepoDetailViewModel(carViewModel: CarViewModel): ViewModel
}