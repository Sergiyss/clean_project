package ru.dev.gamedev.honest_investor.repository

data class Download(
    val id: String,
    val urlString: String,
    val outputFilename: String,
    val progress: Int,
    val downloadAttempt : Int,
    val result: String,
    val state: String)