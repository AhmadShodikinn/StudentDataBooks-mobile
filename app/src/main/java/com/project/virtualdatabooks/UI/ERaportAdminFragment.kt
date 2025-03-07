package com.project.virtualdatabooks.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.AdminViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentERaportAdminBinding

class ERaportAdminFragment : Fragment() {
    private lateinit var binding: FragmentERaportAdminBinding
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var tokenHandler: TokenHandler
    private var userId: Int? = null
    private var studentName: String? = null
    private var studentNISN: String? = null
    private var studentMajor: String? = null

    private var currentSemester = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository, requireContext())
        adminViewModel = ViewModelProvider(this, factory).get(AdminViewModel::class.java)

        userId = arguments?.getInt("USER_ID")
        studentName = arguments?.getString("NAMA")
        studentNISN = arguments?.getString("NISN")
        studentMajor = arguments?.getString("JURUSAN")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentERaportAdminBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId?.let { adminViewModel.getImageRaporById(it, currentSemester) }

        binding.tvRaporName.text = studentName
        binding.tvRaporNisn.text = studentNISN
        binding.tvRaporBidangKeahlian.text = studentMajor
        binding.tvSemester.text = "Semester $currentSemester"

        adminViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        })

        adminViewModel.imageRapor.observe(viewLifecycleOwner, { bitmap ->
            if (bitmap != null) {
                binding.erapor.setImageBitmap(bitmap)
            }
        })

        binding.buttonBack.setOnClickListener {
            if (currentSemester > 1) {
                currentSemester--
                updateLayout()
            }
        }

        binding.buttonNext.setOnClickListener {
            if (currentSemester < 6) {
                currentSemester++
                updateLayout()
            }
        }
    }

    private fun updateLayout() {
        binding.buttonBack.visibility = if (currentSemester == 1) View.GONE else View.VISIBLE
        binding.buttonNext.visibility = if (currentSemester == 6) View.GONE else View.VISIBLE

        userId?.let { adminViewModel.getImageRaporById(it, currentSemester) }

        binding.buttonNext.text = "Semester ${currentSemester + 1}"
        binding.buttonBack.text = "Semester ${currentSemester - 1}"
        binding.tvSemester.text = "Semester $currentSemester"
    }

}