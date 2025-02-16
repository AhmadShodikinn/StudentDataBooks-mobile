package com.project.virtualdatabooks.Network

import com.project.virtualdatabooks.Data.Request.AdminLoginRequest
import com.project.virtualdatabooks.Data.Request.AdminOTPRequest
import com.project.virtualdatabooks.Data.Request.StudentLoginRequest
import com.project.virtualdatabooks.Data.Response.AdminLoginResponse
import com.project.virtualdatabooks.Data.Response.AdminOTPResponse
import com.project.virtualdatabooks.Data.Response.StudentLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login-admin")
    suspend fun adminLogin(@Body adminLoginRequest: AdminLoginRequest): AdminLoginResponse

    @POST("auth/code-admin")
    suspend fun checkOTPadmin(@Body adminOTPRequest: AdminOTPRequest): AdminOTPResponse

    @POST("auth/login-siswa")
    suspend fun studentLogin(@Body studentLoginRequest: StudentLoginRequest): StudentLoginResponse
}