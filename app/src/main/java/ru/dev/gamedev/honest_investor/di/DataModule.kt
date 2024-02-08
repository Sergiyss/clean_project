package ru.dev.gamedev.honest_investor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dev.gamedev.honest_investor.network.network_monitor.ConnectivityManagerNetworkMonitor
import ru.dev.gamedev.honest_investor.network.network_monitor.NetworkMonitor

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
