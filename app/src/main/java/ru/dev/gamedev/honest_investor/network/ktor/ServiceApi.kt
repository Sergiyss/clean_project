package ru.dev.gamedev.honest_investor.network.ktor

import kotlinx.coroutines.flow.Flow

interface ServiceApi {
   // suspend fun login(): NetworkResponse<String>
    suspend fun downloadFileLink(link : String) : Flow<NetworkResponse<String>>
}