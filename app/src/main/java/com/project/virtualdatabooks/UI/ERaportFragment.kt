package com.project.virtualdatabooks.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.StudentViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentERaportBinding


class ERaportFragment : Fragment() {
    private lateinit var binding: FragmentERaportBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var tokenHandler: TokenHandler
    private var userId: Int? = null
    private var currentSemester = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository, requireContext())
        studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        userId = arguments?.getInt("USER_ID")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentERaportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId?.let { studentViewModel.getStudentBiodata(it) }


        studentViewModel.studentBiodata.observe(viewLifecycleOwner, {data ->
            binding.tvRaporName.text = data.dataDiri?.namaLengkap
            binding.tvRaporNisn.text = "NISN (${data.nisn})"
            binding.tvSemester.text = "Semester $currentSemester"

            val bidangKeahlian = data.pendidikan?.diterimaDiBidangKeahlian
            val programKeahlian = data.pendidikan?.diterimaDiBidangKeahlian
            val paketKeahlian = data.pendidikan?.diterimaDiPaketKeahlian

            binding.tvRaporBidangKeahlian.text = "$paketKeahlian/$programKeahlian/$bidangKeahlian"
            binding.tvKelasAbsen.text = data.pendidikan?.diterimaDiKelas.toString()

        })

        studentViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        })


        userId?.let { studentViewModel.getImageRapor(it, currentSemester) }

        studentViewModel.imageRapor.observe(viewLifecycleOwner, { bitmap ->
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

        userId?.let { studentViewModel.getImageRapor(it, currentSemester) }

        binding.buttonNext.text = "Semester ${currentSemester + 1}"
        binding.buttonBack.text = "Semester ${currentSemester - 1}"
        binding.tvSemester.text = "Semester $currentSemester"
    }

}
