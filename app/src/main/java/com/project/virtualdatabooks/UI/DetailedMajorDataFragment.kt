package com.project.virtualdatabooks.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.databinding.FragmentDetailedMajorDataBinding


class DetailedMajorDataFragment : Fragment() {
    private lateinit var binding: FragmentDetailedMajorDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedMajorDataBinding.inflate(inflater, container, false)

        val className = arguments?.getString("CLASS_NAME") ?: "TIDAK ADA DATA"
        val classLogo = arguments?.getString("MAJOR") ?: "TIDAK ADA DATA"

        binding.DetailedMajorDataName.text = className

        when (classLogo) {
            "RPL" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_rpl_foreground)
            "DKV" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_dkv_foreground)
            "TAV" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_tav_foreground)
            "BC" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_broadcast_foreground)
            "ANI" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_animasi_foreground)
            "TKJ" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_tkj_foreground)
            "EI" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_tei_foreground)
            "MEK" -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_mekatro_foreground)
            else -> binding.DetailedMajorDataImg.setImageResource(R.mipmap.ic_rpl_foreground)
        }


        return binding.root
    }

}