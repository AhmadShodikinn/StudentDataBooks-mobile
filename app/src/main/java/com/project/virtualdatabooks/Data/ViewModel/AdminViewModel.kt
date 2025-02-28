package com.project.virtualdatabooks.Data.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.DataClass.ItemAngkatanItem
import com.project.virtualdatabooks.Data.DataClass.ItemJurusanItem
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.PendingDataItem
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: Repository): ViewModel() {
    private val _dashboardData = MutableLiveData<AdminDashboardResponse>()
    val dashboardData: LiveData<AdminDashboardResponse> = _dashboardData

    private val _dataJurusan = MutableLiveData<List<ItemJurusanItem>>()
    val dataJurusan: LiveData<List<ItemJurusanItem>> = _dataJurusan

    private val _dataAngkatan = MutableLiveData<List<ItemAngkatanItem>>()
    val dataAngkatan: LiveData<List<ItemAngkatanItem>> = _dataAngkatan

    private val _listDataPending = MutableLiveData<List<AdminGetPendingResponse>> ()
    val listDataPending: LiveData<List<AdminGetPendingResponse>> = _listDataPending

    fun fetchDashboardData(){
        viewModelScope.launch {
            val response = repository.fetchDashboardData()
            _dashboardData.value = response
        }
    }

    fun getDataJurusan() {
        viewModelScope.launch {
            val response = repository.getDataJurusan()
            _dataJurusan.value = response
        }
    }

    fun getDataAngkatan() {
        viewModelScope.launch {
            val response = repository.getDataAngkatan()
            _dataAngkatan.value = response
        }
    }

    fun getAllPendingRequest() {
        viewModelScope.launch {
            val response = repository.getAllPendingRequest()
            _listDataPending.value = response
        }
    }
}