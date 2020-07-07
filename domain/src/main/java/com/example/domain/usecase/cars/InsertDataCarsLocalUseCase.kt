package com.example.domain.usecase.cars

import com.example.domain.model.Car
import com.example.domain.repository.DataCarRepository
import com.example.domain.usecase.UseCaseInsert
import io.reactivex.Single
import javax.inject.Inject

open class InsertDataCarsLocalUseCase  @Inject constructor(
    private val dataCarRepository: DataCarRepository
) : UseCaseInsert< Single<List<Car>>>() {
    override fun createObservable(cars: List<Car>) {
        dataCarRepository.insertDataCarLocal(cars)
    }

}