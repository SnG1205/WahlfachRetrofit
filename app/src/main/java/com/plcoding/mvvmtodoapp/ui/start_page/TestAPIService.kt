package com.plcoding.mvvmtodoapp.ui.start_page

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import kotlinx.serialization.Serializable
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path


private const val BASE_URL = //"https://android-kotlin-fun-mars-server.appspot.com"
    "http://10.0.2.2:3300"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    //.addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface TestAPIService {
    @GET("api")
    suspend fun getLogins() :  List<Logins> //String
    @GET("api/{username}/{password}")
    suspend fun getUsername(
        @Path("username") username: String?,
        @Path("password") password: String?
    ): List<User>
    /*@GET("api/{username}")
    suspend fun getId(
        @Path("username") username: String?
    ): String*/
    @GET("api/messages/{user_id}")
    suspend fun getMessagesById(
        @Path("user_id") user_id: Int?
    ): List<Messages>
    @POST("api")
    suspend fun createUser(
        @Body user: PostUser?
    )
    @POST("api/messages")
    suspend fun createMessage(
        @Body message: PostMessages?
    )
    @DELETE("api/messages/{user_id}")
    suspend fun deleteMessage(
        @Path("user_id") text: String?
    )
}


object TestApi {
    val retrofitService : TestAPIService by lazy {
        retrofit.create(TestAPIService::class.java)
    }
}