package com.project.virtualdatabooks.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.databinding.FragmentEditDataBinding


class EditDataFragment : Fragment() {
    private lateinit var binding: FragmentEditDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDataBinding.inflate(inflater, container, false)

        binding.informationButton.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, EditFormStudentFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return binding.root
    }

}