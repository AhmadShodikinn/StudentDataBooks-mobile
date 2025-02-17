package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.databinding.FragmentEditFormStudentBinding

class EditFormStudentFragment : Fragment() {
    private lateinit var binding: FragmentEditFormStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                Toast.makeText(context, getString(R.string.SelectedForm), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // Optional: handle when nothing is selected
            }
        }


        return binding.root
    }
}