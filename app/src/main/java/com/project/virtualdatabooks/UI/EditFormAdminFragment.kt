package com.project.virtualdatabooks.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.AdminViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentEditFormAdminBinding


class EditFormAdminFragment : Fragment() {
    private lateinit var binding: FragmentEditFormAdminBinding
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var tokenHandler: TokenHandler
    private var optionSelected: Int? = null
    private var studentId: Int? = null

    private val genderOptions = arrayOf("laki-laki", "perempuan")
    private val parentOptions = arrayOf("yatim", "piatu", "yatim piatu", "lengkap")
    private val liveWithOptions = arrayOf("ortu", "saudara", "lainnya", "wali")
    private val bloodTypeOptions = arrayOf("A", "B", "O", "AB")
    private val fatherStatusOptions = arrayOf("masih hidup", "meninggal")
    private val motherStatusOptions = arrayOf("masih hidup", "meninggal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository)
        adminViewModel = ViewModelProvider(this, factory).get(AdminViewModel::class.java)

        studentId = arguments?.getInt("STUDENT_ID")

        studentId?.let { adminViewModel.getPendingRequestById(it) }

        Log.d("EditFormAdminFragment", "STUDENT_ID: $studentId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditFormAdminBinding.inflate(inflater, container, false)

        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)

        val sexAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        val parentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, parentOptions)
        val liveWithAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, liveWithOptions)
        val bloodTypesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bloodTypeOptions)
        val fatherStatusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fatherStatusOptions)
        val motherStatusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, motherStatusOptions)

        binding.spinnerSex.adapter = sexAdapter
        binding.spinnerParent.adapter = parentAdapter
        binding.spinnerLiveWith.adapter = liveWithAdapter
        binding.spinnerBlood.adapter = bloodTypesAdapter
        binding.spinnerFatherStatus.adapter = fatherStatusAdapter
        binding.spinnerMotherStatus.adapter = motherStatusAdapter

        binding.spinnerOptionForm.adapter = adapter

        fillInForm()

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
                binding.layoutWali.visibility = View.GONE
                binding.layoutHobi.visibility = View.GONE
                binding.iconNoResult.visibility = View.GONE

                optionSelected = position

                when (position) {
                    0 -> {
                        binding.iconNoResult.visibility = View.VISIBLE
                    }
                    1 -> binding.layoutBiodata.visibility = View.VISIBLE
                    2 -> binding.layoutAlamat.visibility = View.VISIBLE
                    3 -> binding.layoutHealth.visibility = View.VISIBLE
                    4 -> binding.layoutPendidikan.visibility = View.VISIBLE
                    5 -> binding.layoutAyah.visibility = View.VISIBLE
                    6 -> binding.layoutIbu.visibility = View.VISIBLE
                    7 -> binding.layoutWali.visibility = View.VISIBLE
                    8 -> binding.layoutPerkembanganPendidikan.visibility = View.VISIBLE
                    9 -> binding.layoutSetelahPendidikan.visibility = View.VISIBLE
                    10 -> binding.layoutHobi.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO()
            }
        }

        binding.acceptedButton.setOnClickListener {
            studentId?.let { id -> adminViewModel.acceptedPendingRequest(id) }

            adminViewModel.acceptedDeclineResponse.observe(viewLifecycleOwner, {response ->
                if (response != null) {
                    val intent = Intent(requireContext(), NotifactionUpdate::class.java)
                    intent.putExtra("HEADER_MESSAGE", "Form Berhasil Dikonfirmasi!")
                    intent.putExtra("SUBTITLE_MESSAGE", "Terimakasih, Form telah berhasil dikonfirmasi !.")
                    activity?.startActivity(intent)
                }
            })

        }

        binding.declinedButton.setOnClickListener {
            studentId?.let { id -> adminViewModel.declinePendingRequest(id) }

            adminViewModel.acceptedDeclineResponse.observe(viewLifecycleOwner, {response ->
                if (response != null) {
                    val intent = Intent(requireContext(), NotifactionUpdate::class.java)
                    intent.putExtra("HEADER_MESSAGE", "Form Berhasil Ditolak!")
                    intent.putExtra("SUBTITLE_MESSAGE", "Terimakasih, Form telah berhasil ditolak !.")
                    activity?.startActivity(intent)
                }
            })
        }

        return binding.root
    }

    private fun fillInForm() {
        adminViewModel.dataPendingRequestByIdResponse.observe(viewLifecycleOwner, {data ->

            Log.d("EditFormAdminFragment", "data: $data")

            data?.data?.dataDiri.let { biodata ->
                binding.edtFullName.setText(biodata?.namaLengkap)
                binding.edtShortName.setText(biodata?.namaPanggilan)
                binding.edtBornPlace.setText(biodata?.tempatLahir)
                binding.edtBornDate.setText(biodata?.tanggalLahir)
                binding.edtReligion.setText(biodata?.agama)
                binding.edtNationality.setText(biodata?.kewarganegaraan)
                binding.edtLanguage.setText(biodata?.bahasaSehariHari)
                binding.edtTotalSiblings.setText(biodata?.jmlSaudaraKandung?.toString())
                binding.edtAdoptedSiblings.setText(biodata?.jmlSaudaraAngkat?.toString())
                binding.edtStepSiblings.setText(biodata?.jmlSaudaraTiri?.toString())
                binding.edtOrderSiblings.setText(biodata?.anakKe?.toString())

                val sexPosition = genderOptions.indexOf(biodata?.jenisKelamin)
                binding.spinnerSex.setSelection(sexPosition)

                val parentPosition = parentOptions.indexOf(biodata?.kelengkapanOrtu)
                binding.spinnerParent.setSelection(parentPosition)


//              disabledFocus
                binding.edtFullName.isFocusable = false
                binding.edtShortName.isFocusable = false
                binding.edtBornPlace.isFocusable = false
                binding.edtBornDate.isFocusable = false
                binding.edtReligion.isFocusable = false
                binding.edtNationality.isFocusable = false
                binding.edtLanguage.isFocusable = false
                binding.edtTotalSiblings.isFocusable = false
                binding.edtAdoptedSiblings.isFocusable = false
                binding.edtStepSiblings.isFocusable = false
                binding.edtOrderSiblings.isFocusable = false

                binding.spinnerParent.isEnabled = false
            }

            data.data?.tempatTinggal.let { tempatTinggal ->
                binding.edtAddress.setText(tempatTinggal?.alamat)
                binding.edtTelephoneNumber.setText(tempatTinggal?.noTelepon)
                binding.edtDistance.setText(tempatTinggal?.jarakKeSekolah)

                val stayWithPosition = liveWithOptions.indexOf(tempatTinggal?.tinggalDengan)
                binding.spinnerLiveWith.setSelection(stayWithPosition)

                //disabled
                binding.edtAddress.isFocusable = false
                binding.edtTelephoneNumber.isFocusable = false
                binding.edtDistance.isFocusable = false

            }

            data.data?.pendidikan.let { pendidikan ->
                binding.edtHighschool.setText(pendidikan?.sebelumnyaTamatanDari)
                binding.edtIjazahNumber.setText(pendidikan?.sebelumnyaTanggalDanIjazah)
                binding.edtSkhunNumber.setText(pendidikan?.sebelumnyaTanggalSkhunDan)
                binding.edtLastLongEdu.setText(pendidikan?.sebelumnyaLamaBelajar)
                binding.edtMoveFrom.setText(pendidikan?.pindahanDariSekolah)
                binding.edtReasonMove.setText(pendidikan?.pindahanAlasan)
                binding.edtAcceptIn.setText(pendidikan?.diterimaDiKelas?.toString())
                binding.edtSkill.setText(pendidikan?.diterimaDiBidangKeahlian)
                binding.edtSpecializeSkill.setText(pendidikan?.diterimaDiProgramKeahlian)
                binding.edtPackageSkill.setText(pendidikan?.diterimaDiPaketKeahlian)
                binding.edtDateAcceptIn.setText(pendidikan?.diterimaTanggal)

                binding.edtHighschool.isFocusable = false
                binding.edtIjazahNumber.isFocusable = false
                binding.edtSkhunNumber.isFocusable = false
                binding.edtLastLongEdu.isFocusable = false
                binding.edtMoveFrom.isFocusable = false
                binding.edtReasonMove.isFocusable = false
                binding.edtAcceptIn.isFocusable = false
                binding.edtSkill.isFocusable = false
                binding.edtSpecializeSkill.isFocusable = false
                binding.edtPackageSkill.isFocusable = false
                binding.edtDateAcceptIn.isFocusable = false
            }

            data.data?.ayahKandung.let { ayahKandung ->
                binding.edtFatherName.setText(ayahKandung?.nama)
                binding.edtFatherReligion.setText(ayahKandung?.agama)
                binding.edtFatherNationality.setText(ayahKandung?.kewarganegaraan)
                binding.edtFatherOccupation.setText(ayahKandung?.pekerjaan)
                binding.edtFatherEducation.setText(ayahKandung?.pendidikan)
                binding.edtFatherExpenditure.setText(ayahKandung?.pengeluaranPerBulan)
                binding.edtFatherAddressAndPhone.setText(ayahKandung?.alamatDanNoTelepon)
                binding.edtFatherBornDate.setText(ayahKandung?.tanggalLahir)

                val fatherPosition = fatherStatusOptions.indexOf(ayahKandung?.status)
                binding.spinnerFatherStatus.setSelection(fatherPosition)

                binding.edtFatherName.isFocusable = false
                binding.edtFatherReligion.isFocusable = false
                binding.edtFatherNationality.isFocusable = false
                binding.edtFatherOccupation.isFocusable = false
                binding.edtFatherEducation.isFocusable = false
                binding.edtFatherExpenditure.isFocusable = false
                binding.edtFatherAddressAndPhone.isFocusable = false
                binding.edtFatherBornDate.isFocusable = false
            }

            data.data?.ibuKandung.let { ibuKandung ->
                binding.edtMotherName.setText(ibuKandung?.nama)
                binding.edtMotherReligion.setText(ibuKandung?.agama)
                binding.edtMotherNationality.setText(ibuKandung?.kewarganegaraan)
                binding.edtMotherOccupation.setText(ibuKandung?.pekerjaan)
                binding.edtMotherEducation.setText(ibuKandung?.pendidikan )
                binding.edtMotherExpenditure.setText(ibuKandung?.pengeluaranPerBulan)
                binding.edtMotherAddressAndPhone.setText(ibuKandung?.alamatDanNoTelepon)
                binding.edtMotherBornDate.setText(ibuKandung?.tanggalLahir)

                binding.edtMotherName.isFocusable = false
                binding.edtMotherReligion.isFocusable = false
                binding.edtMotherNationality.isFocusable = false
                binding.edtMotherOccupation.isFocusable = false
                binding.edtMotherEducation.isFocusable = false
                binding.edtMotherExpenditure.isFocusable = false
                binding.edtMotherAddressAndPhone.isFocusable = false
                binding.edtMotherBornDate.isFocusable = false

                val motherPosition = motherStatusOptions.indexOf(ibuKandung?.status)
                binding.spinnerMotherStatus.setSelection(motherPosition)
            }

            data.data?.wali.let { wali ->
                binding.edtWaliName.setText(wali?.nama)
                binding.edtWaliReligion.setText(wali?.agama)
                binding.edtWaliNationality.setText(wali?.kewarganegaraan )
                binding.edtWaliOccupation.setText(wali?.pekerjaan)
                binding.edtWaliEducation.setText(wali?.pendidikan)
                binding.edtWaliExpenditure.setText(wali?.pengeluaranPerBulan)
                binding.edtWaliAddressAndPhone.setText(wali?.alamatDanNoTelepon)
                binding.edtWaliBornDate.setText(wali?.tanggalLahir)

                binding.edtWaliName.isFocusable = false
                binding.edtWaliReligion.isFocusable = false
                binding.edtWaliNationality.isFocusable = false
                binding.edtWaliOccupation.isFocusable = false
                binding.edtWaliEducation.isFocusable = false
                binding.edtWaliExpenditure.isFocusable = false
                binding.edtWaliAddressAndPhone.isFocusable = false
                binding.edtWaliBornDate.isFocusable = false
            }

            data.data?.setelahPendidikan.let { setelahPendidikan ->
                binding.edtContinueTo.setText(setelahPendidikan?.melanjutkanKe)
                binding.edtWorkInCompany.setText(setelahPendidikan?.bekerjaNamaPerusahaan)
                binding.edtStartWorkDate.setText(setelahPendidikan?.bekerjaTanggalMulai)
                binding.edtIncome.setText(setelahPendidikan?.bekerjaPenghasilan)

                binding.edtContinueTo.isFocusable = false
                binding.edtWorkInCompany.isFocusable = false
                binding.edtStartWorkDate.isFocusable = false
                binding.edtIncome.isFocusable = false
            }

            data.data?.kesehatan.let { kesehatan ->
                binding.edtDeases.setText(kesehatan?.penyakitPernahDiderita)
                binding.edtAbnormality.setText(kesehatan?.kelainanJasmani)
                binding.edtHeight.setText(kesehatan?.tinggi)
                binding.edtWeight.setText(kesehatan?.beratBadan)

                binding.edtDeases.isFocusable = false
                binding.edtAbnormality.isFocusable = false
                binding.edtHeight.isFocusable = false
                binding.edtWeight.isFocusable = false

                val bloodTypesPosition = bloodTypeOptions.indexOf(kesehatan?.golDarah)
                binding.spinnerBlood.setSelection(bloodTypesPosition)
            }

            data.data?.hobiSiswa.let { hobiSiswa ->
                binding.edtKesenian.setText(hobiSiswa?.kesenian)
                binding.edtOlahraga.setText(hobiSiswa?.olahraga)
                binding.edtOrganisasi.setText(hobiSiswa?.organisasi)
                binding.edtLainLain.setText(hobiSiswa?.lainLain)

                binding.edtKesenian.isFocusable = false
                binding.edtOlahraga.isFocusable = false
                binding.edtOrganisasi.isFocusable = false
                binding.edtLainLain.isFocusable = false
            }

            data.data?.perkembangan.let { perkembangan ->
                binding.edtScholarshipReceived.setText(perkembangan?.menerimaBeaSiswaTahunKelasDari)
                binding.edtLeftSchool.setText(perkembangan?.meninggalkanSekolahIniTanggal)
                binding.edtLeftSchoolReason.setText(perkembangan?.meninggalkanSekolahIniAlasan)
                binding.edtEducationEnd.setText(perkembangan?.akhirPendidikanTamatBelajarLulusTahun)
                binding.edtEducationDiplomaNumber.setText(perkembangan?.akhirPendidikanNoTanggalIjazah)
                binding.edtEducationSkhunNumber.setText(perkembangan?.akhirPendidikanNoTanggalSkhun)

                binding.edtScholarshipReceived.isFocusable = false
                binding.edtLeftSchool.isFocusable = false
                binding.edtLeftSchoolReason.isFocusable = false
                binding.edtEducationEnd.isFocusable = false
                binding.edtEducationDiplomaNumber.isFocusable = false
                binding.edtEducationSkhunNumber.isFocusable = false
            }


        })
    }


}