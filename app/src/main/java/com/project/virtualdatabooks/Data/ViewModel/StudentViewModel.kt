package com.project.virtualdatabooks.Data.ViewModel

import android.content.Context
import android.widget.Toast
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
import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateSetelahPendidikanRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateTempatTinggalRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateWaliRequest
import com.project.virtualdatabooks.Data.Response.StudentBiodataResponse
import com.project.virtualdatabooks.Data.Response.StudentUpdateResponse
import kotlinx.coroutines.launch
import org.json.JSONObject

class StudentViewModel(private val repository: Repository, private val context: Context): ViewModel() {
    private val _studentBiodata = MutableLiveData<StudentBiodataResponse>()
    val studentBiodata: LiveData<StudentBiodataResponse> = _studentBiodata

    private val _updateStudentBiodata = MutableLiveData<StudentUpdateResponse>()
    val updateStudentBiodata: LiveData<StudentUpdateResponse> = _updateStudentBiodata

    fun getStudentBiodata(userId: Int){
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