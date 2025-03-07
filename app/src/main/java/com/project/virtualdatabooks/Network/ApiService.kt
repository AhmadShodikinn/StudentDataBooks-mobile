package com.project.virtualdatabooks.Network

import com.project.virtualdatabooks.Data.DataClass.ItemAngkatanItem
import com.project.virtualdatabooks.Data.DataClass.ItemJurusanItem
import com.project.virtualdatabooks.Data.DataClass.ItemSearchItem
import com.project.virtualdatabooks.Data.Request.AdminLoginRequest
import com.project.virtualdatabooks.Data.Request.AdminOTPRequest
import com.project.virtualdatabooks.Data.Request.StudentLoginRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
import com.project.virtualdatabooks.Data.Response.AdminAccDeccResponse
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingRequestByIdResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.AdminLoginResponse
import com.project.virtualdatabooks.Data.Response.AdminOTPResponse
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import com.project.virtualdatabooks.Data.Response.StudentLoginResponse
import com.project.virtualdatabooks.Data.Response.StudentUpdateResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login-admin")
    suspend fun adminLogin(@Body adminLoginRequest: AdminLoginRequest): Response<AdminLoginResponse>

    @POST("auth/code-admin")
    suspend fun checkOTPadmin(@Body adminOTPRequest: AdminOTPRequest): Response<AdminOTPResponse>

    @GET("admin/dashboard")
    suspend fun fetchDashboardData(): Response<AdminDashboardResponse>

    @GET("admin/jurusan")
    suspend fun getJurusanData(): Response<List<ItemJurusanItem>>

    @GET("admin/angkatan")
    suspend fun getAngkatan(): Response<List<ItemAngkatanItem>>

    @GET("admin/data-diri/pending")
    suspend fun getAllPendingRequest(): Response<AdminGetPendingResponse>

    @GET("admin/data-diri/pending/{id}")
    suspend fun getPendingRequestById(@Path("id") id: Int): Response<AdminGetPendingRequestByIdResponse>

    @POST("admin/data-diri/pending/{id}")
    suspend fun acceptedPendingRequest(@Path("id") id: Int): Response<AdminAccDeccResponse>

    @DELETE("admin/data-diri/pending/{id}")
    suspend fun deletePendingRequest(@Path("id") id: Int): Response<AdminAccDeccResponse>

    @GET("admin/akun")
    suspend fun getAkunByMajorYearName(
        @Query("jurusan") jurusan: String,
        @Query("angkatan") angkatan: String,
        @Query("search") search: String
    ): Response<List<ItemSearchItem>>

    @POST("auth/login-siswa")
    suspend fun studentLogin(@Body studentLoginRequest: StudentLoginRequest): Response<StudentLoginResponse>

    @GET("siswa/data-diri")
    suspend fun studentBiodata(@Query("user_id") userId: Int): Response<StudentBiodataResponse>

    @PUT("siswa/data-diri")
    suspend fun updateStudentData(@Body studentUpdateRequest: StudentUpdateRequest): Response<StudentUpdateResponse>

    @GET("siswa/image-raport/{id}")
    @Headers("Accept: image/png")
    suspend fun getImageRapor(
        @Path("id") id: Int,
        @Query("semester") semester: Int
    ): Response<ResponseBody>

    @GET("/admin/export-raport-pdf/{id}")
    suspend fun getExportRaportPdfById(@Path("id") id: Int): Response<ResponseBody>

    @GET("/admin/export-raport-excel/{id}")
    suspend fun getExportRaportExcelById(@Path("id") id: Int): Response<ResponseBody>


    @Multipart
    @POST("import-excel")
    suspend fun postImportByExcel(@Part file: MultipartBody.Part): Response<ResponseBody>


}