package com.project.virtualdatabooks.Data.ViewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import okhttp3.ResponseBody
import org.json.JSONObject

class StudentViewModel(private val repository: Repository, private val context: Context): ViewModel() {
    private val _studentBiodata = MutableLiveData<StudentBiodataResponse>()
    val studentBiodata: LiveData<StudentBiodataResponse> = _studentBiodata

    private val _updateStudentBiodata = MutableLiveData<StudentUpdateResponse>()
    val updateStudentBiodata: LiveData<StudentUpdateResponse> = _updateStudentBiodata

    private val _imageRapor = MutableLiveData<Bitmap>()
    val imageRapor: LiveData<Bitmap> = _imageRapor

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

    fun getImageRapor(id: Int, semester: Int) {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getImageRapor(id, semester)

                if (response.isSuccessful) {
                    val inputStream = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    _imageRapor.value = bitmap

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


}