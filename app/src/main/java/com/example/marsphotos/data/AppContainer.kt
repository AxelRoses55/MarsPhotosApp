package com.example.marsphotos.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com"

    private val retrofit = retrofit2.Retrofit.Builder()
        .addConverterFactory(
            kotlinx.serialization.json.Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: com.example.marsphotos.network.MarsApiService by lazy {
        retrofit.create(com.example.marsphotos.network.MarsApiService::class.java)
    }

    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}