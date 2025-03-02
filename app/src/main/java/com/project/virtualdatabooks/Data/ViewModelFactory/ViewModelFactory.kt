package com.project.virtualdatabooks.Data.ViewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.AdminViewModel
import com.project.virtualdatabooks.Data.ViewModel.StudentViewModel

class ViewModelFactory(private val repository: Repository, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AdminViewModel::class.java) -> {
                AdminViewModel(repository, context) as T
            }
            modelClass.isAssignableFrom(StudentViewModel::class.java) -> {
                StudentViewModel(repository, context) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}