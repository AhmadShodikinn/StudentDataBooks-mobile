package com.project.virtualdatabooks.Data.ViewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.LoginViewModel
import com.project.virtualdatabooks.Support.TokenHandler

class LoginViewModelFactory(
    private val repository: Repository,
    private val tokenHandler: TokenHandler,
    private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository, tokenHandler, context) as T
    }
}