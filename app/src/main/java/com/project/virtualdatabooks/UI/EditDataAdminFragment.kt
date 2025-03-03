package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.Adapter.ListPendingRequestAdapter
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.AdminViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentEditDataAdminBinding

class EditDataAdminFragment: Fragment() {
    private lateinit var binding: FragmentEditDataAdminBinding
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var adapter: ListPendingRequestAdapter
    private lateinit var tokenHandler: TokenHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository, requireContext())

        adminViewModel = ViewModelProvider(this, factory).get(AdminViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDataAdminBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvListPendingRequest
        adapter = ListPendingRequestAdapter(requireContext(), emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adminViewModel.getAllPendingRequest()

        adminViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        })

        adminViewModel.listDataPending.observe(viewLifecycleOwner, {  response ->

            Log.d("EditDataFragment", "Data: $response")

            response?.data?.let { data ->

                val filteredData = data.filterNotNull()
                adapter = ListPendingRequestAdapter(requireContext(), filteredData)
                recyclerView.adapter = adapter
            }

        })
    }

}