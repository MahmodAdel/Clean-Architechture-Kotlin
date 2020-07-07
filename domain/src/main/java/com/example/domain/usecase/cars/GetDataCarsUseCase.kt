package com.example.domain.usecase.cars

import com.example.cleanarchitecture.domain.exception.AlertException
import com.example.domain.model.Car
import com.example.domain.repository.DataCarRepository
import com.example.domain.usecase.UseCase
import io.reactivex.Single
import javax.inject.Inject

open class GetDataCarsUseCase @Inject constructor(
    private val dataCarRepository: DataCarRepository
) : UseCase<GetDataCarsUseCase.Params, Single<List<Car>>>(){




    override fun createObservable(params: Params?): Single<List<Car>?>? {
        return when (params?.pageNumber) {
            null -> Single.error(AlertException(code = -1, message = "Params input not null"))
            else -> dataCarRepository.getDataCar(params.pageNumber)
        }
    }

    data class Params(val pageNumber: Int)



}