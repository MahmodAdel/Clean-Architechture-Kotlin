package com.example.domain.usecase

import com.example.domain.model.Car
import com.example.domain.usecase.cars.GetDataCarsLocalUseCase
import io.reactivex.Single

abstract class UserCaseLocal< out T> where T : Any {

    abstract fun createObservable(): Single<List<Car>?>?

    open fun onCleared() {}
}