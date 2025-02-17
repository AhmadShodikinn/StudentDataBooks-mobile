package com.project.virtualdatabooks.Data.ViewModel

import android.util.Log
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

class LoginViewModel(
    private val repository: Repository,
    private val tokenHandler: TokenHandler
): ViewModel() {
    private val _adminLoginResult = MutableLiveData<AdminLoginResponse>()
    val adminLoginResult: LiveData<AdminLoginResponse> = _adminLoginResult

    private val _checkOTPAdminResult = MutableLiveData<AdminOTPResponse>()
    val checkOTPAdminResult: LiveData<AdminOTPResponse> = _checkOTPAdminResult

    private val _studentLoginResult = MutableLiveData<StudentLoginResponse>()
    val studentLoginResult: LiveData<StudentLoginResponse> = _studentLoginResult

    fun loginAdmin(username: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginAdmin(username, password)
            _adminLoginResult.value = response
        }
    }

    fun sendOTPAdmin(code: String){
        viewModelScope.launch {
            val response = repository.sendOTPAdmin(code)
            _checkOTPAdminResult.value = response

            response.token?.let {
                tokenHandler.saveToken(it)
            }

            Log.d("LoginViewModel", "tokenAdmin: ${tokenHandler.getToken()}" )
        }
    }

    fun loginStudent(nisn: String, dateBirth: String){
        viewModelScope.launch {
            val response = repository.loginStudent(nisn, dateBirth)
            _studentLoginResult.value = response

            response.token?.let {
                tokenHandler.saveToken(it)
            }

            Log.d("LoginViewModel", "token: ${tokenHandler.getToken()}" )
        }
    }

}