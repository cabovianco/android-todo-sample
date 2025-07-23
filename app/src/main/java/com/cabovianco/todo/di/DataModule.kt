package com.cabovianco.todo.di

import android.content.Context
import androidx.room.Room
import com.cabovianco.todo.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "DATABASE"
        ).build()

    @Provides
    fun provideTaskDao(appDatabase: AppDatabase) =
        appDatabase.taskDao()
}
