package com.project.virtualdatabooks.Network

import com.google.gson.GsonBuilder
import com.project.virtualdatabooks.Support.CustomDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object{

        private const val BASE_URL = "http://10.0.2.2:8080/"

        fun getApiService(token: String): ApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val gson = GsonBuilder()
                .registerTypeAdapter(String::class.java,CustomDeserializer())
                .create()

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(TokenInterceptor(token))
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}