package com.project.virtualdatabooks.Data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: Repository): ViewModel() {
    private val _dashboardData = MutableLiveData<AdminDashboardResponse>()
    val dashboardData: LiveData<AdminDashboardResponse> = _dashboardData

    fun fetchDashboardData(){
        viewModelScope.launch {
            val response = repository.fetchDashboardData()
            _dashboardData.value = response
        }
    }
}