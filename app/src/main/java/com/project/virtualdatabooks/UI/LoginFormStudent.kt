package com.project.virtualdatabooks.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.LoginViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.LoginViewModelFactory
import com.project.virtualdatabooks.MainActivity
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.ActivityLoginFormStudentBinding

class LoginFormStudent: AppCompatActivity() {
    private lateinit var binding: ActivityLoginFormStudentBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginFormStudentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val tokenHandler = TokenHandler(this)
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = LoginViewModelFactory(repository, tokenHandler, this)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        loginViewModel.studentLoginResult.observe(this, { response ->
            if (response != null) {
                if (response.isMatch == true) {
                    val userId = response.id
                    if (userId != null) {
                        navigateToHome(userId)
                        finish()
                    }
                } else {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonSecondary.setOnClickListener{
            val nisn = binding.inputNisn.editText?.text.toString()
            val dateBirth = binding.inputDateBirth.editText?.text.toString()

            if (nisn.isNotEmpty() && dateBirth.isNotEmpty()) {
                loginViewModel.loginStudent(nisn, dateBirth)
            } else {
                Toast.makeText(this, "field kosong!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun navigateToHome(userId: Int) {
        Toast.makeText(this, "Login berhasil!, Selamat datang", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("USER_ID", userId)
        intent.putExtra("IS_ADMIN", false)
        startActivity(intent)
    }
}