package com.project.virtualdatabooks.Data.Repository

import com.project.virtualdatabooks.Data.Request.AdminLoginRequest
import com.project.virtualdatabooks.Data.Request.AdminOTPRequest
import com.project.virtualdatabooks.Data.Request.StudentLoginRequest
import com.project.virtualdatabooks.Data.Response.AdminLoginResponse
import com.project.virtualdatabooks.Data.Response.AdminOTPResponse
import com.project.virtualdatabooks.Data.Response.StudentLoginResponse
import com.project.virtualdatabooks.Network.ApiService

class LoginRepository(private val apiService: ApiService) {

    suspend fun loginAdmin(username: String, password: String): AdminLoginResponse {
        val bodyRequest = AdminLoginRequest(username, password)
        return apiService.adminLogin(bodyRequest)
    }

    suspend fun sendOTPAdmin(code: String): AdminOTPResponse {
        val bodyRequest = AdminOTPRequest(code)
        return apiService.checkOTPadmin(bodyRequest)
    }

    suspend fun loginStudent(nisn: String, dateBirth: String): StudentLoginResponse {
        val bodyRequest = StudentLoginRequest(nisn, dateBirth)
        return apiService.studentLogin(bodyRequest)
    }

}