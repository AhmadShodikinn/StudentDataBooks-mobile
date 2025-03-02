package com.project.virtualdatabooks.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.AdminViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.Data.Adapter.ItemJurusanAdapter
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentStudentDataBinding


class StudentDataFragment : Fragment() {
    private lateinit var binding: FragmentStudentDataBinding
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var adapter: ItemJurusanAdapter
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
        binding = FragmentStudentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvJurusan
        adapter = ItemJurusanAdapter(requireContext(), emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adminViewModel.getDataJurusan()

        adminViewModel.dataJurusan.observe(viewLifecycleOwner, { data ->
            adapter = ItemJurusanAdapter(requireContext(), data)
            recyclerView.adapter = adapter
        })

    }


}