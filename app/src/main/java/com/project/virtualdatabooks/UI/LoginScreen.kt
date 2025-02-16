package com.project.virtualdatabooks.UI

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.project.virtualdatabooks.databinding.ActivityLoginScreenBinding

class LoginScreen: AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonPrimary.setOnClickListener {
            val intent = Intent(this, LoginFormStudent::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonSecondary.setOnClickListener{
            val intent = Intent(this, LoginFormAdmin::class.java)
            startActivity(intent)
            finish()
        }
    }
}