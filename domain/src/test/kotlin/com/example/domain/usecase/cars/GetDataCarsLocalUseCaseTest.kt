package com.example.domain.usecase.cars

import com.example.domain.repository.DataCarRepository
import com.example.domain.usecase.createCar
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import java.util.*

class GetDataCarsLocalUseCaseTest{
    private lateinit var getDataCarsLocalUseCase: GetDataCarsLocalUseCase
    private val carRepository: DataCarRepository= mock(DataCarRepository::class.java)

    @Before
    fun setup(){
        getDataCarsLocalUseCase=GetDataCarsLocalUseCase(carRepository)
    }

    @After
    fun clear(){
        getDataCarsLocalUseCase.onCleared()
    }

    @Test
    fun createObservable(){
        getDataCarsLocalUseCase.createObservable()
        verify(carRepository).getDataCarLocal()
    }

    @Test
    fun createObservableNull(){
        val useCase=getDataCarsLocalUseCase.createObservable()?.test()
        useCase?.assertError{true}
    }

    @Test
    fun getDataComplete(){
        given(carRepository.getDataCarLocal()).willReturn(Single.just(Arrays.asList(createCar())))

        val test = getDataCarsLocalUseCase.createObservable()?.test()

        test?.assertComplete()
    }

    @Test
    fun getDataReturnData(){
        val items = Arrays.asList(createCar())
        given(carRepository.getDataCarLocal()).willReturn(Single.just(items))
        val test = getDataCarsLocalUseCase.createObservable()?.test()
        test?.assertValue(items)
    }




}