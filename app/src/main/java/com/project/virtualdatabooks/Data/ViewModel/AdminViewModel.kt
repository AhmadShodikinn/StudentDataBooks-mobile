package com.project.virtualdatabooks.Data.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.DataClass.ItemAngkatanItem
import com.project.virtualdatabooks.Data.DataClass.ItemJurusanItem
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Response.AdminAccDeccResponse
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingRequestByIdResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.PendingDataDiri
import com.project.virtualdatabooks.Data.Response.PendingDataItem
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class AdminViewModel(private val repository: Repository, private val context: Context): ViewModel() {
    private val _dashboardData = MutableLiveData<AdminDashboardResponse>()
    val dashboardData: LiveData<AdminDashboardResponse> = _dashboardData

    private val _dataJurusan = MutableLiveData<List<ItemJurusanItem>>()
    val dataJurusan: LiveData<List<ItemJurusanItem>> = _dataJurusan

    private val _dataAngkatan = MutableLiveData<List<ItemAngkatanItem>>()
    val dataAngkatan: LiveData<List<ItemAngkatanItem>> = _dataAngkatan

    private val _listDataPending = MutableLiveData<AdminGetPendingResponse> ()
    val listDataPending: LiveData<AdminGetPendingResponse> = _listDataPending

    private val _dataPendingRequestById = MutableLiveData<AdminGetPendingRequestByIdResponse> ()
    val dataPendingRequestByIdResponse: LiveData<AdminGetPendingRequestByIdResponse> = _dataPendingRequestById

    private val _acceptedDeclineResponse = MutableLiveData<AdminAccDeccResponse> ()
    val acceptedDeclineResponse: LiveData<AdminAccDeccResponse> = _acceptedDeclineResponse

    fun fetchDashboardData(){
        viewModelScope.launch {
            try {
                val response = repository.fetchDashboardData()

                if (response.isSuccessful) {
                    _dashboardData.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getDataJurusan() {
        viewModelScope.launch {
            try {
                val response = repository.getDataJurusan()

                if (response.isSuccessful) {
                    _dataJurusan.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getDataAngkatan() {
        viewModelScope.launch {
            try {
                val response = repository.getDataAngkatan()

                if (response.isSuccessful) {
                    _dataAngkatan.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getAllPendingRequest() {
        viewModelScope.launch {
            try {
                val response = repository.getAllPendingRequest()

                if (response.isSuccessful) {
                    _listDataPending.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("error")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getPendingRequestById(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPendingRequestById(id)

                if (response.isSuccessful) {
                    _dataPendingRequestById.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun acceptedPendingRequest(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.acceptedPendingRequest(id)

                if (response.isSuccessful) {
                    _acceptedDeclineResponse.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun declinePendingRequest(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deletePendingRequest(id)

                if (response.isSuccessful) {
                    _acceptedDeclineResponse.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Server Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}