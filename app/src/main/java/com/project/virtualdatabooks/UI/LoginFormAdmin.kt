package com.project.virtualdatabooks.UI

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.LoginRepository
import com.project.virtualdatabooks.Data.ViewModel.LoginViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.LoginViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.ActivityLoginFormAdminBinding

class LoginFormAdmin: AppCompatActivity() {
    private lateinit var binding: ActivityLoginFormAdminBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginFormAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val tokenHandler = TokenHandler(this)
        val token = tokenHandler.getToken() ?: ""

        val loginRepository = LoginRepository(ApiConfig.getApiService(token))
        val factory = LoginViewModelFactory(loginRepository, tokenHandler)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        loginViewModel.adminLoginResult.observe(this, { response ->
            if (response != null) {
                if (response.code != null){
                    Toast.makeText(this, "Sukses!, Mengalihkan...", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginFormOTPAdmin::class.java)
                    startActivity(intent)
                    finish()
                } else if (response.message != null){
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonSecondary.setOnClickListener {
            val username = binding.inputUsername.editText?.text.toString()
            val password = binding.inputPassword.editText?.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.loginAdmin(username, password)
            } else {
                Toast.makeText(this, "field kosong!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}