package com.project.virtualdatabooks.Data.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import com.project.virtualdatabooks.Data.Response.StudentUpdateResponse
import kotlinx.coroutines.launch
import org.json.JSONObject

class StudentViewModel(private val repository: Repository, private val context: Context): ViewModel() {
    private val _studentBiodata = MutableLiveData<StudentBiodataResponse>()
    val studentBiodata: LiveData<StudentBiodataResponse> = _studentBiodata

    private val _updateStudentBiodata = MutableLiveData<StudentUpdateResponse>()
    val updateStudentBiodata: LiveData<StudentUpdateResponse> = _updateStudentBiodata

    val isLoading = MutableLiveData<Boolean>(false)

    fun getStudentBiodata(userId: Int){
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.fetchBiodataSiswa(userId)

                if (response.isSuccessful) {
                    _studentBiodata.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateStudentData(request: StudentUpdateRequest) {
        viewModelScope.launch {
            try {
                val response = repository.updateStudentData(request)

                if (response.isSuccessful) {
                    _updateStudentBiodata.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}