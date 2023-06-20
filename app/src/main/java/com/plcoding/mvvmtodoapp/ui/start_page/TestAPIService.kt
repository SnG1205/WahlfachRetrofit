package com.plcoding.mvvmtodoapp.ui.start_page

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import kotlinx.serialization.Serializable
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType


private const val BASE_URL = //"https://android-kotlin-fun-mars-server.appspot.com"
    "http://10.0.2.2:3300"

private val retrofit = Retrofit.Builder()
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface TestAPIService {
    @GET("api")
    suspend fun getLogins() :  String //String
}


object TestApi {
    val retrofitService : TestAPIService by lazy {
        retrofit.create(TestAPIService::class.java)
    }
}