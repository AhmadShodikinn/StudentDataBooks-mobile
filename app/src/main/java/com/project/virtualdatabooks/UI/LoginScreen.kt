package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.project.virtualdatabooks.databinding.ActivityLoginScreenBinding

class LoginScreen: AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        enableEdgeToEdge()
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        
    }
}