package com.project.virtualdatabooks.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.Adapter.ItemAngkatanAdapter
import com.project.virtualdatabooks.Data.DataClass.ItemAngkatanItem
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.AdminViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentDetailedMajorStudentBinding

class DetailedMajorStudentFragment : Fragment() {
    private lateinit var binding: FragmentDetailedMajorStudentBinding
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var adapter: ItemAngkatanAdapter
    private lateinit var tokenHandler: TokenHandler
    private lateinit var jurusan: String
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository)

        adminViewModel = ViewModelProvider(this, factory).get(AdminViewModel::class.java)

        position = arguments?.getInt("POSITION", -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedMajorStudentBinding.inflate(inflater, container, false)

        val position = arguments?.getInt("POSITION") ?: 0
        jurusan = when (position) {
            1 -> "Rekayasa Perangkat Lunak"
            2 -> "Desain Komunikasi Visual"
            3 -> "Audio Video"
            4 -> "Broadcasting"
            5 -> "Animasi"
            6 -> "Teknik Komunikasi Jaringan"
            7 -> "Elektronika Industri"
            8 -> "Mekatronika"
            else -> "Rekayasa Perangkat Lunak"
        }

        binding.buttonBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvDetailJurusan
        adapter = ItemAngkatanAdapter(requireContext(), emptyList())
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        adminViewModel.getDataAngkatan()

        adminViewModel.dataAngkatan.observe(viewLifecycleOwner, {data ->

            val adjustData = data.map {
                ItemAngkatanItem(
                    id = it.id,
                    tahun = it.tahun,
                    jurusan = jurusan
                )
            }

            adapter = ItemAngkatanAdapter(requireContext(), adjustData)
            recyclerView.adapter = adapter
        })
    }

}