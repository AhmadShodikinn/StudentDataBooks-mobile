package com.project.virtualdatabooks.UI

import android.content.Intent
import android.os.Bundle
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
import com.project.virtualdatabooks.databinding.ActivityFormOtpAdminBinding

class LoginFormOTPAdmin: AppCompatActivity() {
    private lateinit var binding: ActivityFormOtpAdminBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormOtpAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val tokenHandler = TokenHandler(this)
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = LoginViewModelFactory(repository, tokenHandler)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        loginViewModel.checkOTPAdminResult.observe(this, { response ->
            if (response != null) {
                if (response.id != null && response.username != null && response.email != null && response.token != null) {
                    navigateToHome()
                    finish()
                } else if (response.message != null) {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonSecondary.setOnClickListener {
            val codePart1 = binding.OTP1.editText?.text.toString()
            val codePart2 = binding.OTP2.editText?.text.toString()
            val codePart3 = binding.OTP3.editText?.text.toString()
            val codePart4 = binding.OTP4.editText?.text.toString()
            val codePart5 = binding.OTP5.editText?.text.toString()

            val codeOTP = codePart1 + codePart2 + codePart3 + codePart4 + codePart5
            if (codePart1.isNotEmpty() && codePart2.isNotEmpty() && codePart3.isNotEmpty() && codePart4.isNotEmpty() && codePart5.isNotEmpty()) {
                loginViewModel.sendOTPAdmin(codeOTP)
            } else {
                Toast.makeText(this, "field kosong!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun navigateToHome() {
        Toast.makeText(this, "Login berhasil. Selamat datang", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}