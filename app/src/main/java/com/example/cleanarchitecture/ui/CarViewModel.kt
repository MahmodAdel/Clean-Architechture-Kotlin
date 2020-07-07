package com.example.cleanarchitecture.ui

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.cleanarchitecture.base.BaseViewModel
import com.example.cleanarchitecture.model.CarItem
import com.example.cleanarchitecture.model.CarItemMapper
import com.example.cleanarchitecture.util.RxUtils
import com.example.domain.model.Car
import com.example.domain.usecase.cars.GetDataCarsLocalUseCase
import com.example.domain.usecase.cars.GetDataCarsUseCase
import com.example.domain.usecase.cars.InsertDataCarsLocalUseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CarViewModel @Inject constructor(
    private val getDataCarsUseCase: GetDataCarsUseCase,
    private val carItemMapper: CarItemMapper,
    private val getDataCarsLocalUseCase: GetDataCarsLocalUseCase,
    private val insertDataCarsLocalUseCase: InsertDataCarsLocalUseCase
):BaseViewModel(){

    val repoCars= MutableLiveData<List<CarItem>>()
    val loading = MutableLiveData<Boolean>().apply { postValue(false) }
    val query = MutableLiveData<String>()


 /*   fun getCars(page: Int): Observable<List<Car>> {
        return Observable.concatArray(
            getUsersFromDb(page),
            getUsersFromApi(page))
    }*/


    @SuppressLint("CheckResult")
    fun getCars(page: Int):Observable<List<Car>>{
        return Observable.concatArray(
            getUsersFromDb(),
            getUsersFromApi(page)
        )
    }

    @SuppressLint("CheckResult")
    fun getCarsMore(page: Int):Observable<List<Car>>{
        return Observable.concatArray(getUsersFromApi(page))
    }



   /* fun getCarsApi(page: Int) = getDataCarsUseCase.createObservable(GetDataCarsUseCase.Params(pageNumber = page))
        ?.compose(RxUtils.applySingleScheduler(loading))
        ?.doFinally{
            loading.value == false
        }
        ?.map { it.map {
            carItemMapper.mapToPresentation(it)
        } }
        ?.subscribe(
            {
               // repoCars.value = it
               storeUsersInDb(it)
            },
            {
                Timber.e("Get repo error: $it")
                setThrowable(it)
            })
        ?.add(this)*/

    fun getUsersFromApi(page: Int): Observable<List<Car>?>? {
        return getDataCarsUseCase.createObservable(GetDataCarsUseCase.Params(
            pageNumber = page
        ))?.compose(RxUtils.applySingleScheduler(loading))
            ?.doFinally{
                loading.value== false
            }
            ?.toObservable()
            ?.doOnNext {
                Timber.d("Dispatching ${it?.size} users from API...")
                storeUsersInDb(it)
            }

    }


/*
    fun getUsersFromDb()= getDataCarsLocalUseCase.createObservable()
        ?.compose(RxUtils.applySingleScheduler(loading))
        ?.doFinally{
            loading.value == false
        }
        ?.map { it.map {
            carItemMapper.mapToPresentation(it)
        } }
        ?.subscribe(
            {
                 repoCars.value = it

            },
            {
                Timber.e("Get repo error: $it")
                setThrowable(it)
            })
        ?.add(this)
*/
    fun getUsersFromDb(): Observable<List<Car>?>? {
        return getDataCarsLocalUseCase.createObservable()
            ?.compose(RxUtils.applySingleScheduler(loading))
            ?.doFinally{
                loading.value== false
            }
            ?.toObservable()
            ?.doOnNext{
                it?.map {
                    carItemMapper.mapToPresentation(it)
                }

            }
    }

    @SuppressLint("CheckResult")
    fun storeUsersInDb(users: List<Car>?) {
        Observable.fromCallable {
            users?.let { insertDataCarsLocalUseCase.createObservable(it) }

        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Timber.d("Inserted ${users?.size} users from API in DB...")
            }
    }
}
