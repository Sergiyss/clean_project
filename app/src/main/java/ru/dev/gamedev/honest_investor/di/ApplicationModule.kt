package ru.dev.gamedev.honest_investor.di

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


import io.ktor.client.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.cache.storage.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import ru.dev.gamedev.honest_investor.App
import ru.dev.gamedev.honest_investor.network.ktor.ServiceApi
import ru.dev.gamedev.honest_investor.network.ktor.ServiceApiImpl
import ru.dev.gamedev.honest_investor.repository.DownloadRepository
import java.nio.file.*


@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideClient(
        @ApplicationContext context: Context,
    ): HttpClient {
        return HttpClient(Android) {

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL

            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = false
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }
            install(Auth) {
                bearer {

                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideServiceApi(serviceApiImpl: ServiceApiImpl): ServiceApi = serviceApiImpl


    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideApplication(
        @ApplicationContext context : Context
    ): App {
        return context as App
    }

    @Provides
    @Singleton
    fun providerDownloadRepository(
        context: Context,
        workManager: WorkManager
    ): DownloadRepository {
        return DownloadRepository(context, workManager)
    }

    @Provides
    @Singleton
    fun providerWorkManage(
        app: App
    ) : WorkManager = app.workManager

}