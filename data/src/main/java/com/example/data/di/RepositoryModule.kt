package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.Constants
import com.example.data.DataCarRepositoryImpI
import com.example.data.local.db.AppDatabase
import com.example.domain.repository.DataCarRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule{
    @Provides
    @DatabaseInfo
    fun providerDatabaseName(): String {
        return Constants.DATABASE_NAME
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName).fallbackToDestructiveMigration()
            .build()
    }



    @Provides
    @Singleton
    fun provideCarRepository(repository: DataCarRepositoryImpI):DataCarRepository{
        return repository
    }


}