package com.project.virtualdatabooks.Data.Repository

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
import com.project.virtualdatabooks.Data.Response.AdminAccDeccResponse
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingRequestByIdResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.AdminLoginResponse
import com.project.virtualdatabooks.Data.Response.AdminOTPResponse
import com.project.virtualdatabooks.Data.Response.PendingDataDiri
import com.project.virtualdatabooks.Data.Response.PendingDataItem
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import com.project.virtualdatabooks.Data.Response.StudentLoginResponse
import com.project.virtualdatabooks.Data.Response.StudentUpdateResponse
import com.project.virtualdatabooks.Network.ApiService
import retrofit2.Response

class Repository(private val apiService: ApiService) {

    suspend fun loginAdmin(username: String, password: String): Response<AdminLoginResponse> {
        val bodyRequest = AdminLoginRequest(username, password)
        return apiService.adminLogin(bodyRequest)
    }

    suspend fun sendOTPAdmin(code: String): Response<AdminOTPResponse> {
        val bodyRequest = AdminOTPRequest(code)
        return apiService.checkOTPadmin(bodyRequest)
    }

    suspend fun loginStudent(nisn: String, dateBirth: String): Response<StudentLoginResponse> {
        val bodyRequest = StudentLoginRequest(nisn, dateBirth)
        return apiService.studentLogin(bodyRequest)
    }

    suspend fun fetchDashboardData(): Response<AdminDashboardResponse> {
        return apiService.fetchDashboardData()
    }

    suspend fun fetchBiodataSiswa(userId: Int): Response<StudentBiodataResponse> {
        return apiService.studentBiodata(userId)
    }

    suspend fun updateStudentData(request: StudentUpdateRequest): Response<StudentUpdateResponse> {
        return apiService.updateStudentData(request)
    }

    suspend fun getDataJurusan(): Response<List<ItemJurusanItem>> {
        return apiService.getJurusanData()
    }

    suspend fun getDataAngkatan(): Response<List<ItemAngkatanItem>> {
        return apiService.getAngkatan()
    }

    suspend fun getAllPendingRequest(): Response<AdminGetPendingResponse> {
        return apiService.getAllPendingRequest()
    }

    suspend fun getPendingRequestById(id: Int): Response<AdminGetPendingRequestByIdResponse> {
        return apiService.getPendingRequestById(id)
    }

    suspend fun acceptedPendingRequest(id: Int): Response<AdminAccDeccResponse> {
        return apiService.acceptedPendingRequest(id)
    }

    suspend fun deletePendingRequest(id: Int): Response<AdminAccDeccResponse> {
        return apiService.deletePendingRequest(id)
    }


}