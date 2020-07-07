package com.example.domain.repository

import com.example.domain.model.Car
import io.reactivex.Observable
import io.reactivex.Single

interface DataCarRepository{
fun getDataCar(page: Int): Single<List<Car>?>?
    fun getDataCarLocal() :Single<List<Car>?>?
    fun insertDataCarLocal(cars:List<Car>)
}