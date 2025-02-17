package com.project.virtualdatabooks.Data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
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

    fun updateStudentBiodata(request: StudentUpdateRequest) {
        viewModelScope.launch {
            val response = repository.updateBiodataSiswa(request)
            _updateStudentBiodata.value = response
        }
    }

}