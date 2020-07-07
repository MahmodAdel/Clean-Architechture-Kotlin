package com.example.domain.usecase.cars

import com.example.domain.repository.DataCarRepository
import com.example.domain.usecase.createCar
import io.reactivex.Single
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*

class GetDataCarsUseCaseTest {

    private lateinit var getDataCarsUseCase: GetDataCarsUseCase
    private val carRepository: DataCarRepository=mock(DataCarRepository::class.java)


    @Before
    fun setup(){
        getDataCarsUseCase= GetDataCarsUseCase(carRepository)
    }

    @After
    fun clear(){
        getDataCarsUseCase.onCleared()
    }


    @Test
    fun createObservable() {
        val params=GetDataCarsUseCase.Params(pageNumber = anyInt())
        getDataCarsUseCase.createObservable(params)
        verify(carRepository).getDataCar(params.pageNumber)
    }


    @Test
    fun createObservableNull(){
        val useCase=getDataCarsUseCase.createObservable(null)?.test()
        useCase?.assertError{true}
    }


    @Test
    fun getDataComplete(){
        given(carRepository.getDataCar(anyInt())).willReturn(Single.just(Arrays.asList(createCar())))
        val test= getDataCarsUseCase.createObservable(GetDataCarsUseCase.Params(anyInt()))?.test()
        test?.assertComplete()
    }


    @Test
    fun getDataReturn(){
        val params= GetDataCarsUseCase.Params(pageNumber = anyInt())
        val items= Arrays.asList(createCar())

        given(carRepository.getDataCar(params.pageNumber)).willReturn(Single.just(items))

        val test=getDataCarsUseCase.createObservable(params)?.test()

        test?.assertValue(items)
    }



}