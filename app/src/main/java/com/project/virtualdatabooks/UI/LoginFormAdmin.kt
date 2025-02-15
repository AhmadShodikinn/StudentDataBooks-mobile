package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.project.virtualdatabooks.databinding.ActivityLoginFormAdminBinding

class LoginFormAdmin: AppCompatActivity() {
    private lateinit var binding: ActivityLoginFormAdminBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        enableEdgeToEdge()
        binding = ActivityLoginFormAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}