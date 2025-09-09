package com.example.musicplayer.utils.di

import com.example.musicplayer.utils.coroutine.AppScope
import com.example.musicplayer.utils.logging.AppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    companion object {
        @Provides
        @Singleton
        fun providesAppScope(): AppScope = object : AppScope {
            override val coroutineContext: CoroutineContext =
                Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { context, throwable ->
                    AppLogger.instance.error(
                        "Error in AppScope. Context = $context, Error = $throwable"
                    )
                }

        }
    }
}