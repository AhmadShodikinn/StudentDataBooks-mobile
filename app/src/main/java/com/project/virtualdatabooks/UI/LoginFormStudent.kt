package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.project.virtualdatabooks.databinding.ActivityLoginFormStudentBinding

class LoginFormStudent: AppCompatActivity() {
    private lateinit var binding: ActivityLoginFormStudentBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        enableEdgeToEdge()
        binding = ActivityLoginFormStudentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}