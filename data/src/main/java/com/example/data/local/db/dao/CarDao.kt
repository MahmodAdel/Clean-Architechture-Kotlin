package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.CarEntity
import io.reactivex.Single
@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(carEntity: CarEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(carEntitys: List<CarEntity>)

    @Query("SELECT * FROM car")
    fun getAllCars():Single<List<CarEntity>>

    @Query("SELECT * FROM car")
    fun getAll():Single<CarEntity>


}