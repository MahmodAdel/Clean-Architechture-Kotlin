package com.example.domain.usecase.cars

import com.example.domain.repository.DataCarRepository
import com.example.domain.usecase.createCar
import io.reactivex.Single
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.Mockito.anyList
import org.mockito.Mockito.verify
import java.util.*

class InsertDataCarsLocalUseCaseTest {
    private lateinit var insertDataCarsUseCase: InsertDataCarsLocalUseCase
    private val carRepository: DataCarRepository = Mockito.mock(DataCarRepository::class.java)


    @Before
    fun setup(){
        insertDataCarsUseCase= InsertDataCarsLocalUseCase(carRepository)
    }

    @After
    fun clear(){
        insertDataCarsUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        val items=Arrays.asList(createCar())
        insertDataCarsUseCase.createObservable(items)
        verify(carRepository).insertDataCarLocal(items)
    }


}