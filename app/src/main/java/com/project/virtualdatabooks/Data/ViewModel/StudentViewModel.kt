package com.project.virtualdatabooks.Data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: Repository): ViewModel() {
    private val _studentBiodata = MutableLiveData<StudentBiodataResponse>()
    val studentBiodata: LiveData<StudentBiodataResponse> = _studentBiodata

    fun getStudentBiodata(userId: Int){
        viewModelScope.launch {
            val response = repository.fetchBiodataSiswa(userId)
            _studentBiodata.value = response
        }
    }

}