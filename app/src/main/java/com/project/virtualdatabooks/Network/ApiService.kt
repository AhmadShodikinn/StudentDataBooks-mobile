package com.project.virtualdatabooks.Network

import com.project.virtualdatabooks.Data.DataClass.ItemAngkatanItem
import com.project.virtualdatabooks.Data.DataClass.ItemJurusanItem
import com.project.virtualdatabooks.Data.Request.AdminLoginRequest
import com.project.virtualdatabooks.Data.Request.AdminOTPRequest
import com.project.virtualdatabooks.Data.Request.StudentLoginRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateAyahRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateDataDiriRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateHobiRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateIbuRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateKesehatanRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdatePendidikanRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdatePerkembanganRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateSetelahPendidikanRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateTempatTinggalRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateWaliRequest
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.AdminLoginResponse
import com.project.virtualdatabooks.Data.Response.AdminOTPResponse
import com.project.virtualdatabooks.Data.Response.PendingDataDiri
import com.project.virtualdatabooks.Data.Response.PendingDataItem
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import com.project.virtualdatabooks.Data.Response.StudentLoginResponse
import com.project.virtualdatabooks.Data.Response.StudentUpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login-admin")
    suspend fun adminLogin(@Body adminLoginRequest: AdminLoginRequest): AdminLoginResponse

    @POST("auth/code-admin")
    suspend fun checkOTPadmin(@Body adminOTPRequest: AdminOTPRequest): AdminOTPResponse

    @GET("admin/dashboard")
    suspend fun fetchDashboardData(): AdminDashboardResponse

    @GET("admin/jurusan")
    suspend fun getJurusanData(): List<ItemJurusanItem>

    @GET("admin/angkatan")
    suspend fun getAngkatan(): List<ItemAngkatanItem>

    @GET("admin/data-diri/pending")
    suspend fun getAllPendingRequest(): AdminGetPendingResponse

    @POST("auth/login-siswa")
    suspend fun studentLogin(@Body studentLoginRequest: StudentLoginRequest): StudentLoginResponse

    @GET("siswa/data-diri")
    suspend fun studentBiodata(@Query("user_id") userId: Int): StudentBiodataResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentData(@Body studentUpdateRequest: StudentUpdateRequest): StudentUpdateResponse
}