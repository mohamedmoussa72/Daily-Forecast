package com.orcas.dailyforecast.di.utile

import android.content.Context
import com.orcas.data.utile.CoroutineAppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Singleton
    @Provides
    fun provideCoroutineAppExecutor(@ApplicationContext appContext: Context): CoroutineAppExecutors {
        return CoroutineAppExecutors()
    }
}