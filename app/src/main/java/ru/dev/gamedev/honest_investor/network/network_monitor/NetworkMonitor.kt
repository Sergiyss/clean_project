package ru.dev.gamedev.honest_investor.network.network_monitor

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}