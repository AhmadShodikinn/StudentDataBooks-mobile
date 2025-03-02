package com.project.virtualdatabooks.Data.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Response.AdminLoginResponse
import com.project.virtualdatabooks.Data.Response.AdminOTPResponse
import com.project.virtualdatabooks.Data.Response.StudentLoginResponse
import com.project.virtualdatabooks.Support.TokenHandler
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel(
    private val repository: Repository,
    private val tokenHandler: TokenHandler,
    private val context: Context
): ViewModel() {
    private val _adminLoginResult = MutableLiveData<AdminLoginResponse>()
    val adminLoginResult: LiveData<AdminLoginResponse> = _adminLoginResult

    private val _checkOTPAdminResult = MutableLiveData<AdminOTPResponse>()
    val checkOTPAdminResult: LiveData<AdminOTPResponse> = _checkOTPAdminResult

    private val _studentLoginResult = MutableLiveData<StudentLoginResponse>()
    val studentLoginResult: LiveData<StudentLoginResponse> = _studentLoginResult

    fun loginAdmin(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginAdmin(username, password)

                if (response.isSuccessful) {
                    _adminLoginResult.value = response.body()
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

    fun sendOTPAdmin(code: String){
        viewModelScope.launch {
            try {
                val response = repository.sendOTPAdmin(code)

                if (response.isSuccessful) {
                    _checkOTPAdminResult.value = response.body()

                    response.body()?.token?.let {
                        tokenHandler.saveToken(it)
                    }
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

    fun loginStudent(nisn: String, dateBirth: String){
        viewModelScope.launch {
            try {
                val response = repository.loginStudent(nisn, dateBirth)

                if (response.isSuccessful) {
                    _studentLoginResult.value = response.body()

                    response.body()?.token?.let {
                        tokenHandler.saveToken(it)
                    }
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