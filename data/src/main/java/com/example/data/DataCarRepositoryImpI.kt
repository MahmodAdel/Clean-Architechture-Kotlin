package com.example.data

import com.example.data.local.db.dao.CarDao
import com.example.data.model.CarEntityMapper
import com.example.data.remote.api.CarApi
import com.example.data.remote.response.DataRepoResponse
import com.example.domain.model.Car
import com.example.domain.repository.DataCarRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DataCarRepositoryImpI @Inject constructor(
    private val dataCarsApi : CarApi,
    private val carDao: CarDao,
    private val dataCarEntityMapper: CarEntityMapper
): DataCarRepository {
    override fun getDataCar(page: Int): Single<List<Car>?>? {
            return dataCarsApi.getResponse(page = if (page == null) 0 else page)
                ?.map{ response ->
                    response?.map { dataCarEntityMapper.mapToDomain(it) }
                }
                ?.doOnError{

                    Throwable("Not found "+it.message)}
    }

    override fun getDataCarLocal(): Single<List<Car>?>? {
        return carDao.getAllCars().map{
            response-> response.map {
            dataCarEntityMapper.mapToDomain(it)
        }
        }
            .doOnError{

                Throwable("Not Found"+it.message)
            }
    }

    override fun insertDataCarLocal(cars: List<Car>) {
        carDao.insertAll(
            cars.map { response->
                dataCarEntityMapper.mapToEntity(response)
            }

        )
    }


}