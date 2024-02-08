package ru.dev.gamedev.honest_investor.repository

import kotlinx.coroutines.flow.Flow

interface Repository<T, V> {

    fun adds(t : T)

    fun observe() : Flow<V>
}