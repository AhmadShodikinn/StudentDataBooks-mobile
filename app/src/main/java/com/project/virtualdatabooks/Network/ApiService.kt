package com.project.virtualdatabooks.Network

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
import com.project.virtualdatabooks.Data.Request.StudentUpdateSetelahPendidikanRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateTempatTinggalRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateWaliRequest
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import com.project.virtualdatabooks.Data.Response.AdminLoginResponse
import com.project.virtualdatabooks.Data.Response.AdminOTPResponse
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import com.project.virtualdatabooks.Data.Response.StudentLoginResponse
import com.project.virtualdatabooks.Data.Response.StudentUpdateResponse
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

    @POST("auth/login-siswa")
    suspend fun studentLogin(@Body studentLoginRequest: StudentLoginRequest): StudentLoginResponse

    @GET("siswa/data-diri")
    suspend fun studentBiodata(@Query("user_id") userId: Int): StudentBiodataResponse

    //updateBiodata
    @PUT("siswa/data-diri")
    suspend fun updateStudentBio(@Body studentUpdateDataDiriRequest: StudentUpdateDataDiriRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentAyah(@Body studentUpdateAyahRequest: StudentUpdateAyahRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentIbu(@Body studentUpdateIbuRequest: StudentUpdateIbuRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentHobi(@Body studentUpdateHobiRequest: StudentUpdateHobiRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentKesehatan(@Body studentUpdateKesehatanRequest: StudentUpdateKesehatanRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentPendidikan(@Body studentUpdatePendidikanRequest: StudentUpdatePendidikanRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentPerkembagan(@Body studentUpdatePerkembanganRequest: StudentUpdatePerkembanganRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentTempatTinggal(@Body studentUpdateTempatTinggalRequest: StudentUpdateTempatTinggalRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentSetelahPendidikan(@Body studentUpdateSetelahPendidikanRequest: StudentUpdateSetelahPendidikanRequest): StudentUpdateResponse

    @PUT("siswa/data-diri")
    suspend fun updateStudentWali(@Body studentUpdateWaliRequest: StudentUpdateWaliRequest): StudentUpdateResponse

}