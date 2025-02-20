package com.project.virtualdatabooks.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.databinding.FragmentEditDataBinding


class EditDataFragment : Fragment() {
    private lateinit var binding: FragmentEditDataBinding
    private var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        userId = arguments?.getInt("USER_ID")

        Log.d("EditFormStudentFragment", "USER_ID: $userId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDataBinding.inflate(inflater, container, false)

        binding.informationButton.setOnClickListener {
            val bundle = Bundle()
            userId?.let { data -> bundle.putInt("USER_ID", data) }

            val fragment = EditFormStudentFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

}