package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Request.AyahKandung
import com.project.virtualdatabooks.Data.ViewModel.StudentViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentEditFormStudentBinding

class EditFormStudentFragment : Fragment() {
    private lateinit var binding: FragmentEditFormStudentBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var tokenHandler: TokenHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository)
        studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditFormStudentBinding.inflate(inflater, container, false)

        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerOptionForm.adapter = adapter

        binding.spinnerOptionForm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                binding.layoutBiodata.visibility = View.GONE
                binding.layoutAlamat.visibility = View.GONE
                binding.layoutHealth.visibility = View.GONE
                binding.layoutPendidikan.visibility = View.GONE
                binding.layoutAyah.visibility = View.GONE
                binding.layoutIbu.visibility = View.GONE
                binding.layoutPerkembanganPendidikan.visibility = View.GONE
                binding.layoutSetelahPendidikan.visibility = View.GONE

                when (position) {
                    1 -> binding.layoutBiodata.visibility = View.VISIBLE
                    2 -> binding.layoutAlamat.visibility = View.VISIBLE
                    3 -> binding.layoutHealth.visibility = View.VISIBLE
                    4 -> binding.layoutPendidikan.visibility = View.VISIBLE
                    5 -> binding.layoutAyah.visibility = View.VISIBLE
                    6 -> binding.layoutIbu.visibility = View.VISIBLE
                    7 -> binding.layoutPerkembanganPendidikan.visibility = View.VISIBLE
                    8 -> binding.layoutSetelahPendidikan.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                binding.sendButton.visibility = View.GONE
            }
        }

        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.sendButton.setOnClickListener {
            getDatainput()
        }


        return binding.root
    }

    private fun getDatainput() {

    }
}