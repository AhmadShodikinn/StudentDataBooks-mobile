package com.project.virtualdatabooks.UI

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.Adapter.ListSearchResultAdapter
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.AdminViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentDetailedMajorDataBinding


class DetailedMajorDataFragment : Fragment() {
    private lateinit var binding: FragmentDetailedMajorDataBinding
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var adapter: ListSearchResultAdapter
    private lateinit var tokenHandler: TokenHandler
    private var majorFullName: String = ""
    private var year: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository, requireContext())

        adminViewModel = ViewModelProvider(this, factory).get(AdminViewModel::class.java)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, REQUEST_CODE_STORAGE_PERMISSION)
            } else {
                Toast.makeText(requireContext(), "Izin sudah diberikan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedMajorDataBinding.inflate(inflater, container, false)

        majorFullName = arguments?.getString("MAJOR_FULLNAME") ?: "TIDAK ADA DATA"
        year = arguments?.getString("YEAR") ?: "TIDAK ADA DATA"

        Log.d("DetailedMajorData", "MajorFullName: $majorFullName")
        Log.d("DetailedMajorData", "year: $year")

        val className = arguments?.getString("CLASS_NAME") ?: "TIDAK ADA DATA"
        val classLogo = arguments?.getString("MAJOR") ?: "TIDAK ADA DATA"

        binding.DetailedMajorDataName.text = className

        when (classLogo) {
            "RPL" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_rpl_foreground)
            "DKV" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_dkv_foreground)
            "TAV" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_tav_foreground)
            "BC" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_broadcast_foreground)
            "ANI" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_animasi_foreground)
            "TKJ" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_tkj_foreground)
            "EI" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_tei_foreground)
            "MEK" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_mekatro_foreground)
            else -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_rpl_foreground)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvDetailedMajorData
        adapter = ListSearchResultAdapter(requireContext(), emptyList()) { id ->
            adminViewModel.getRaportExportById(id)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val searchView: SearchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                searchText?.let {
                    adminViewModel.getAkunByMajorYearName(
                        majorFullName,
                        year,
                        searchText)
                }
                return true
            }
        })

        adminViewModel.listDataSearchByMajorYearName.observe(viewLifecycleOwner, { data ->
            adapter = ListSearchResultAdapter(requireContext(), data)  { id ->
                adminViewModel.getRaportExportById(id)
            }
            recyclerView.adapter = adapter
        })

    }

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 100
    }
}