package com.project.virtualdatabooks.Data.Repository

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
import com.project.virtualdatabooks.Network.ApiService

class Repository(private val apiService: ApiService) {

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

    suspend fun fetchDashboardData(): AdminDashboardResponse {
        return apiService.fetchDashboardData()
    }

    suspend fun fetchBiodataSiswa(userId: Int): StudentBiodataResponse {
        return apiService.studentBiodata(userId)
    }

    suspend fun updateBiodataSiswa(request: StudentUpdateDataDiriRequest): StudentUpdateResponse {
        return apiService.updateStudentBio(request)
    }

    suspend fun updateAyahSiswa(request: StudentUpdateAyahRequest): StudentUpdateResponse {
        return apiService.updateStudentAyah(request)
    }

    suspend fun updateIbuSiswa(request: StudentUpdateIbuRequest): StudentUpdateResponse {
        return apiService.updateStudentIbu(request)
    }

    suspend fun updateHobiSiswa(request: StudentUpdateHobiRequest): StudentUpdateResponse {
        return apiService.updateStudentHobi(request)
    }

    suspend fun updateKesehatanSiswa(request: StudentUpdateKesehatanRequest): StudentUpdateResponse {
        return apiService.updateStudentKesehatan(request)
    }

    suspend fun updatePendidikanSiswa(request: StudentUpdatePendidikanRequest): StudentUpdateResponse {
        return apiService.updateStudentPendidikan(request)
    }

    suspend fun updatePerkembanganSiswa(request: StudentUpdatePerkembanganRequest): StudentUpdateResponse {
        return apiService.updateStudentPerkembagan(request)
    }

    suspend fun updateTempatTinggalSiswa(request: StudentUpdateTempatTinggalRequest): StudentUpdateResponse {
        return apiService.updateStudentTempatTinggal(request)
    }

    suspend fun updateSetelahPendidikanSiswa(request: StudentUpdateSetelahPendidikanRequest): StudentUpdateResponse {
        return apiService.updateStudentSetelahPendidikan(request)
    }

    suspend fun updateWaliSiswa(request: StudentUpdateWaliRequest): StudentUpdateResponse {
        return apiService.updateStudentWali(request)
    }



}