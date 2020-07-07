package com.example.domain.usecase

import com.example.domain.model.Car
import io.reactivex.Single

abstract class UseCaseInsert< out T> where T : Any {

    abstract fun createObservable(cars: List<Car>)

    open fun onCleared() {}
}