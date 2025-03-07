package com.project.virtualdatabooks.Data.ViewModel

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.virtualdatabooks.Data.DataClass.ItemAngkatanItem
import com.project.virtualdatabooks.Data.DataClass.ItemJurusanItem
import com.project.virtualdatabooks.Data.DataClass.ItemSearchItem
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Response.AdminAccDeccResponse
import com.project.virtualdatabooks.Data.Response.AdminDashboardResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingRequestByIdResponse
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.PendingDataDiri
import com.project.virtualdatabooks.Data.Response.PendingDataItem
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AdminViewModel(private val repository: Repository, private val context: Context): ViewModel() {
    private val _dashboardData = MutableLiveData<AdminDashboardResponse>()
    val dashboardData: LiveData<AdminDashboardResponse> = _dashboardData

    private val _dataJurusan = MutableLiveData<List<ItemJurusanItem>>()
    val dataJurusan: LiveData<List<ItemJurusanItem>> = _dataJurusan

    private val _dataAngkatan = MutableLiveData<List<ItemAngkatanItem>>()
    val dataAngkatan: LiveData<List<ItemAngkatanItem>> = _dataAngkatan

    private val _listDataPending = MutableLiveData<AdminGetPendingResponse>()
    val listDataPending: LiveData<AdminGetPendingResponse> = _listDataPending

    private val _dataPendingRequestById = MutableLiveData<AdminGetPendingRequestByIdResponse>()
    val dataPendingRequestByIdResponse: LiveData<AdminGetPendingRequestByIdResponse> = _dataPendingRequestById

    private val _acceptedDeclineResponse = MutableLiveData<AdminAccDeccResponse>()
    val acceptedDeclineResponse: LiveData<AdminAccDeccResponse> = _acceptedDeclineResponse

    private val _listDataSearchByMajorYearName = MutableLiveData<List<ItemSearchItem>>()
    val listDataSearchByMajorYearName: LiveData<List<ItemSearchItem>> = _listDataSearchByMajorYearName

    private val _importStatus = MutableLiveData<String>()
    val importStatus: LiveData<String> get() = _importStatus

    val isLoading = MutableLiveData<Boolean>(false)

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
        isLoading.value = true

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
            } finally {
                isLoading.value = false
            }
        }
    }

    fun getPendingRequestById(id: Int) {
        isLoading.value = true

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
            } finally {
                isLoading.value = false
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

    fun getAkunByMajorYearName(
        jurusan: String,
        angkatan: String,
        search: String
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getAkunByMajorYearName(jurusan, angkatan, search)

                if (response.isSuccessful) {
                    _listDataSearchByMajorYearName.value = response.body()
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

    fun getRaportExportPdfById(id: Int) {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getRaportPdfById(id)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        savePdfToStorage(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Server Error!", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun savePdfToStorage(response: ResponseBody) {
        try {
            val fileName = "report_${System.currentTimeMillis()}.pdf"
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

            val inputStream: InputStream = response.byteStream()
            val outputStream: FileOutputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            inputStream.close()
            outputStream.close()

            Toast.makeText(context, "File berhasil disimpan di: ${file.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal menyimpan file PDF!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRaportExportExcelById(id: Int) {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getRaportExcelById(id)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        saveExcelToStorage(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody).getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Server Error!", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun saveExcelToStorage(response: ResponseBody) {
        try {
            val fileName = "exported_file_${System.currentTimeMillis()}.xlsx"
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

            val inputStream: InputStream = response.byteStream()
            val outputStream: FileOutputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            inputStream.close()
            outputStream.close()

            Toast.makeText(context, "File berhasil disimpan di: ${file.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal menyimpan file Excel!", Toast.LENGTH_SHORT).show()
        }
    }

    fun postImportByExcel(file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = repository.postImportByExcel(file)

                if (response.isSuccessful) {
                    _importStatus.value = "File uploaded successfully!"
                } else {
                    _importStatus.value = "Failed to upload file: ${response.message()}"
                }
            } catch (e: Exception) {
                _importStatus.value = "Error: ${e.message}"
                Log.d("AdminViewModel", "Error: ${e.message}")
            }
        }
    }


}