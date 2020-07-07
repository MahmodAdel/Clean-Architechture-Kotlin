package com.example.domain.usecase

import com.example.domain.model.Car
import com.example.domain.usecase.cars.GetDataCarsLocalUseCase
import com.example.domain.usecase.cars.GetDataCarsUseCase
import io.reactivex.Single

/**
 * If Y want create something from base
 * Please code in here
 * UseCase<Type>
 */
abstract class UseCase<in Params, out T> where T : Any {

    abstract fun createObservable(params: GetDataCarsUseCase.Params? = null): Single<List<Car>?>?

    open fun onCleared() {}
}