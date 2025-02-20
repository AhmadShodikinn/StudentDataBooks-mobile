package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.virtualdatabooks.databinding.FragmentEditDataAdminBinding

class EditDataAdminFragment: Fragment() {
    private lateinit var binding: FragmentEditDataAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDataAdminBinding.inflate(inflater, container, false)


        return binding.root
    }

}