package com.example.domain.usecase.cars

import com.example.cleanarchitecture.domain.exception.AlertException
import com.example.domain.model.Car
import com.example.domain.repository.DataCarRepository
import com.example.domain.usecase.UseCase
import com.example.domain.usecase.UserCaseLocal
import io.reactivex.Single
import javax.inject.Inject

open class GetDataCarsLocalUseCase @Inject constructor(
    private val dataCarRepository: DataCarRepository
) : UserCaseLocal< Single<List<Car>>>(){


    override fun createObservable(): Single<List<Car>?>? {
        return dataCarRepository.getDataCarLocal()
    }
    data class Params(val pageNumber: Int)


}
