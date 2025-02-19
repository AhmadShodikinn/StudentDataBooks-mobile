package com.project.virtualdatabooks.Data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.Repository.Repository
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
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import com.project.virtualdatabooks.Data.Response.StudentUpdateResponse
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: Repository): ViewModel() {
    private val _studentBiodata = MutableLiveData<StudentBiodataResponse>()
    val studentBiodata: LiveData<StudentBiodataResponse> = _studentBiodata

    private val _updateStudentBiodata = MutableLiveData<StudentUpdateResponse>()
    val updateStudentBiodata: LiveData<StudentUpdateResponse> = _updateStudentBiodata

    fun getStudentBiodata(userId: Int){
        viewModelScope.launch {
            val response = repository.fetchBiodataSiswa(userId)
            _studentBiodata.value = response
        }
    }

    fun updateStudentBiodata(request: StudentUpdateDataDiriRequest) {
        viewModelScope.launch {
            val response = repository.updateBiodataSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update ayah siswa
    fun updateStudentAyah(request: StudentUpdateAyahRequest) {
        viewModelScope.launch {
            val response = repository.updateAyahSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update ibu siswa
    fun updateStudentIbu(request: StudentUpdateIbuRequest) {
        viewModelScope.launch {
            val response = repository.updateIbuSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update hobi siswa
    fun updateStudentHobi(request: StudentUpdateHobiRequest) {
        viewModelScope.launch {
            val response = repository.updateHobiSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update kesehatan siswa
    fun updateStudentKesehatan(request: StudentUpdateKesehatanRequest) {
        viewModelScope.launch {
            val response = repository.updateKesehatanSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update pendidikan siswa
    fun updateStudentPendidikan(request: StudentUpdatePendidikanRequest) {
        viewModelScope.launch {
            val response = repository.updatePendidikanSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update perkembangan siswa
    fun updateStudentPerkembangan(request: StudentUpdatePerkembanganRequest) {
        viewModelScope.launch {
            val response = repository.updatePerkembanganSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update tempat tinggal siswa
    fun updateStudentTempatTinggal(request: StudentUpdateTempatTinggalRequest) {
        viewModelScope.launch {
            val response = repository.updateTempatTinggalSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update setelah pendidikan siswa
    fun updateStudentSetelahPendidikan(request: StudentUpdateSetelahPendidikanRequest) {
        viewModelScope.launch {
            val response = repository.updateSetelahPendidikanSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

    // Fungsi untuk update wali siswa
    fun updateStudentWali(request: StudentUpdateWaliRequest) {
        viewModelScope.launch {
            val response = repository.updateWaliSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

}