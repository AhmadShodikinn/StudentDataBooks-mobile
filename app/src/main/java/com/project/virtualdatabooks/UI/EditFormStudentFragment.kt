package com.project.virtualdatabooks.UI

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.Request.DataAyah
import com.project.virtualdatabooks.Data.Request.DataDiri
import com.project.virtualdatabooks.Data.Request.DataHobi
import com.project.virtualdatabooks.Data.Request.DataIbu
import com.project.virtualdatabooks.Data.Request.DataKesehatan
import com.project.virtualdatabooks.Data.Request.DataPendidikan
import com.project.virtualdatabooks.Data.Request.DataPerkembangan
import com.project.virtualdatabooks.Data.Request.DataSetelahPendidikan
import com.project.virtualdatabooks.Data.Request.DataTempatTinggal
import com.project.virtualdatabooks.Data.Request.DataWali
import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
//import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
//import com.project.virtualdatabooks.Data.Request.TempatTinggal
//import com.project.virtualdatabooks.Data.Request.Wali
import com.project.virtualdatabooks.Data.ViewModel.StudentViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentEditFormStudentBinding
import java.text.SimpleDateFormat
import java.util.Locale

class EditFormStudentFragment : Fragment() {
    private lateinit var binding: FragmentEditFormStudentBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var tokenHandler: TokenHandler
    private var optionSelected: Int? = null
    private var userId:Int? = null

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
        studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)


        userId = arguments?.getInt("USER_ID")
        userId?.let { studentViewModel.getStudentBiodata(it) }


        Log.d("EditFormStudentFragment", "USER_ID: $userId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditFormStudentBinding.inflate(inflater, container, false)

        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)

        val sexAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        val parentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, parentOptions)
        val liveWithAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, liveWithOptions)
        val bloodTypesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bloodTypeOptions)
        val fatherStatusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fatherStatusOptions)
        val motherStatusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, motherStatusOptions)

        binding.spinnerSex.adapter = sexAdapter
        binding.spinnerOptionForm.adapter = adapter
        binding.spinnerParent.adapter = parentAdapter
        binding.spinnerLiveWith.adapter = liveWithAdapter
        binding.spinnerBlood.adapter = bloodTypesAdapter
        binding.spinnerFatherStatus.adapter = fatherStatusAdapter
        binding.spinnerMotherStatus.adapter = motherStatusAdapter

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

        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.sendButton.setOnClickListener {
            val requestBody = (
                StudentUpdateRequest(
                    data_diri = getBiodata(),
                    perkembangan = getPerkembanganPendidikan(),
                    ayah_kandung = getAyah(),
                    ibu_kandung = getIbu(),
                    kesehatan = getKesehatan(),
                    pendidikan = getPendidikan(),
                    setelah_pendidikan = getSetelahPendidikan(),
                    tempat_tinggal = getAlamat(),
                    wali = getWali(),
                    hobi_siswa = getHobi()
                )
            )
            studentViewModel.updateStudentData(requestBody)
        }

        studentViewModel.updateStudentBiodata.observe( viewLifecycleOwner, { response ->
            if (response.message != null){
                    val intent = Intent(requireContext(), NotifactionUpdate::class.java)
                    intent.putExtra("HEADER_MESSAGE", "Form Berhasil Diajukan!")
                    intent.putExtra("SUBTITLE_MESSAGE", "Terimakasih, Form telah berhasil diajukan !.")
                    activity?.startActivity(intent)
                }
            }
        )

        return binding.root
    }

    private fun fillInForm(){
        studentViewModel.studentBiodata.observe(viewLifecycleOwner, {data ->
            data?.dataDiri?.let { biodata ->
                binding.edtFullName.setText(biodata.namaLengkap)
                binding.edtShortName.setText(biodata.namaPanggilan)
                binding.edtBornPlace.setText(biodata.tempatLahir)
                binding.edtBornDate.setText(biodata.tanggalLahir)
                binding.edtReligion.setText(biodata.agama)
                binding.edtNationality.setText(biodata.kewarganegaraan)
                binding.edtLanguage.setText(biodata.bahasaSehariHari)
                binding.edtTotalSiblings.setText(biodata.jmlSaudaraKandung?.toString())
                binding.edtAdoptedSiblings.setText(biodata.jmlSaudaraAngkat?.toString())
                binding.edtStepSiblings.setText(biodata.jmlSaudaraTiri?.toString())
                binding.edtOrderSiblings.setText(biodata.anakKe?.toString())

                val sexPosition = genderOptions.indexOf(biodata.jenisKelamin)
                binding.spinnerSex.setSelection(sexPosition)

                val parentPosition = parentOptions.indexOf(biodata.kelengkapanOrtu)
                binding.spinnerParent.setSelection(parentPosition)
            }

            data?.tempatTinggal?.let { tempatTinggal ->
                binding.edtAddress.setText(tempatTinggal.alamat)
                binding.edtTelephoneNumber.setText(tempatTinggal.noTelepon)
                binding.edtDistance.setText(tempatTinggal.jarakKeSekolah)

                val stayWithPosition = liveWithOptions.indexOf(tempatTinggal.tinggalDengan)
                binding.spinnerLiveWith.setSelection(stayWithPosition)
            }

            data?.pendidikan?.let { pendidikan ->
                binding.edtHighschool.setText(pendidikan.sebelumnyaTamatanDari)
                binding.edtIjazahNumber.setText(pendidikan.sebelumnyaTanggalDanIjazah)
                binding.edtSkhunNumber.setText(pendidikan.sebelumnyaTanggalSkhunDan)
                binding.edtLastLongEdu.setText(pendidikan.sebelumnyaLamaBelajar)
                binding.edtMoveFrom.setText(pendidikan.pindahanDariSekolah)
                binding.edtReasonMove.setText(pendidikan.pindahanAlasan)
                binding.edtAcceptIn.setText(pendidikan.diterimaDiKelas?.toString())
                binding.edtSkill.setText(pendidikan.diterimaDiBidangKeahlian)
                binding.edtSpecializeSkill.setText(pendidikan.diterimaDiProgramKeahlian)
                binding.edtPackageSkill.setText(pendidikan.diterimaDiPaketKeahlian)
                binding.edtDateAcceptIn.setText(pendidikan.diterimaTanggal)
            }

            data?.ayahKandung?.let { ayahKandung ->
                binding.edtFatherName.setText(ayahKandung.nama)
                binding.edtFatherReligion.setText(ayahKandung.agama)
                binding.edtFatherNationality.setText(ayahKandung.kewarganegaraan)
                binding.edtFatherOccupation.setText(ayahKandung.pekerjaan)
                binding.edtFatherEducation.setText(ayahKandung.pendidikan)
                binding.edtFatherExpenditure.setText(ayahKandung.pengeluaranPerBulan)
                binding.edtFatherAddressAndPhone.setText(ayahKandung.alamatDanNoTelepon)
                binding.edtFatherBornDate.setText(ayahKandung.tanggalLahir)

                val fatherPosition = fatherStatusOptions.indexOf(ayahKandung.status)
                binding.spinnerFatherStatus.setSelection(fatherPosition)
            }

            data?.ibuKandung?.let { ibuKandung ->
                binding.edtMotherName.setText(ibuKandung.nama)
                binding.edtMotherReligion.setText(ibuKandung.agama)
                binding.edtMotherNationality.setText(ibuKandung.kewarganegaraan)
                binding.edtMotherOccupation.setText(ibuKandung.pekerjaan)
                binding.edtMotherEducation.setText(ibuKandung.pendidikan )
                binding.edtMotherExpenditure.setText(ibuKandung.pengeluaranPerBulan)
                binding.edtMotherAddressAndPhone.setText(ibuKandung.alamatDanNoTelepon)
                binding.edtMotherBornDate.setText(ibuKandung.tanggalLahir)

                val motherPosition = motherStatusOptions.indexOf(ibuKandung.status)
                binding.spinnerMotherStatus.setSelection(motherPosition)
            }

            data?.wali?.let { wali ->
                binding.edtWaliName.setText(wali.nama)
                binding.edtWaliReligion.setText(wali.agama)
                binding.edtWaliNationality.setText(wali.kewarganegaraan )
                binding.edtWaliOccupation.setText(wali.pekerjaan)
                binding.edtWaliEducation.setText(wali.pendidikan)
                binding.edtWaliExpenditure.setText(wali.pengeluaranPerBulan)
                binding.edtWaliAddressAndPhone.setText(wali.alamatDanNoTelepon)
                binding.edtWaliBornDate.setText(wali.tanggalLahir)

            }


            data?.setelahPendidikan?.let { setelahPendidikan ->
                binding.edtContinueTo.setText(setelahPendidikan.melanjutkanKe)
                binding.edtWorkInCompany.setText(setelahPendidikan.bekerjaNamaPerusahaan)
                binding.edtStartWorkDate.setText(setelahPendidikan.bekerjaTanggalMulai)
                binding.edtIncome.setText(setelahPendidikan.bekerjaPenghasilan)
            }

            data?.kesehatan?.let { kesehatan ->
                binding.edtDeases.setText(kesehatan.penyakitPernahDiderita)
                binding.edtAbnormality.setText(kesehatan.kelainanJasmani)
                binding.edtHeight.setText(kesehatan.tinggi)
                binding.edtWeight.setText(kesehatan.beratBadan)

                val bloodTypesPosition = bloodTypeOptions.indexOf(kesehatan.golDarah)
                binding.spinnerBlood.setSelection(bloodTypesPosition)
            }

            data?.hobiSiswa?.let { hobiSiswa ->
                binding.edtKesenian.setText(hobiSiswa.kesenian)
                binding.edtOlahraga.setText(hobiSiswa.olahraga)
                binding.edtOrganisasi.setText(hobiSiswa.organisasi)
                binding.edtLainLain.setText(hobiSiswa.lainLain)
            }

            data?.perkembangan?.let { perkembangan ->
                binding.edtScholarshipReceived.setText(perkembangan.menerimaBeaSiswaTahunKelasDari)
                binding.edtLeftSchool.setText(perkembangan.meninggalkanSekolahIniTanggal)
                binding.edtLeftSchoolReason.setText(perkembangan.meninggalkanSekolahIniAlasan)
                binding.edtEducationEnd.setText(perkembangan.akhirPendidikanTamatBelajarLulusTahun)
                binding.edtEducationDiplomaNumber.setText(perkembangan.akhirPendidikanNoTanggalIjazah)
                binding.edtEducationSkhunNumber.setText(perkembangan.akhirPendidikanNoTanggalSkhun)
            }
        })
    }

    private fun getHobi(): DataHobi {
        val kesenian = binding.edtKesenian.text.toString()
        val olahraga = binding.edtOlahraga.text.toString()
        val organisasi = binding.edtOrganisasi.text.toString()
        val lainLain = binding.edtLainLain.text.toString()

        return DataHobi(
            kesenian,
            olahraga,
            organisasi,
            lainLain
        )
    }

    private fun getSetelahPendidikan(): DataSetelahPendidikan {
        val melanjutkanKe = binding.edtContinueTo.text.toString().takeIf { it.isNotEmpty() }
        val bekerjaDiPerusahaan = binding.edtWorkInCompany.text.toString().takeIf { it.isNotEmpty() }
        var bekerjaTanggalMulai = binding.edtStartWorkDate.text.toString().takeIf { it.isNotEmpty() }
        val penghasilan = binding.edtIncome.text.toString().takeIf { it.isNotEmpty() }

        return DataSetelahPendidikan(
            melanjutkanKe,
            bekerjaDiPerusahaan,
            bekerjaTanggalMulai,
            penghasilan
        )
    }

    private fun getPerkembanganPendidikan(): DataPerkembangan {
        val menerimaBeasiswaTahunKelasDari = binding.edtScholarshipReceived.text.toString()
        val meninggalkanSekolahIniAlasan = binding.edtLeftSchoolReason.text.toString()
        val akhirPendidikanTamatBelajarLulusTahun = binding.edtEducationEnd.text.toString()
        val akhirPendidikanNoTanggalIjazah = binding.edtEducationDiplomaNumber.text.toString()
        val akhirPendidikanNoTanggalSkhun = binding.edtEducationSkhunNumber.text.toString()
        var meninggalkanSekolahIniTanggal = binding.edtLeftSchool.text.toString().takeIf { it.isNotEmpty() }

        return DataPerkembangan(
            menerimaBeasiswaTahunKelasDari,
            meninggalkanSekolahIniTanggal,
            meninggalkanSekolahIniAlasan,
            akhirPendidikanTamatBelajarLulusTahun,
            akhirPendidikanNoTanggalIjazah,
            akhirPendidikanNoTanggalSkhun
        )
    }


    private fun getWali(): DataWali {
        val namaWali = binding.edtWaliName.text.toString()
        var tanggalLahirWali = binding.edtWaliBornDate.text.toString().takeIf { it.isNotEmpty() }
        val tempatLahirWali = binding.edtWaliBornPlace.text.toString()
        val agamaWali = binding.edtWaliReligion.text.toString()
        val kewarganegaraanWali = binding.edtWaliNationality.text.toString()
        val pekerjaanWali = binding.edtWaliOccupation.text.toString()
        val pendidikanWali = binding.edtWaliEducation.text.toString()
        val pengeluaranWali = binding.edtWaliExpenditure.text.toString()
        val alamatWali = binding.edtWaliAddressAndPhone.text.toString()

        return DataWali(
            namaWali,
            tempatLahirWali,
            tanggalLahirWali,
            agamaWali,
            kewarganegaraanWali,
            pendidikanWali,
            pekerjaanWali,
            pengeluaranWali,
            alamatWali,
        )
    }


    private fun getIbu(): DataIbu {
        val namaIbu = binding.edtMotherName.text.toString()
        val statusIbu = binding.spinnerMotherStatus.selectedItem.toString()
        var tanggalLahirIbu = binding.edtMotherBornDate.text.toString().takeIf { it.isNotEmpty() }
        val tempatLahirIbu = binding.edtMotherBornPlace.text.toString()
        val agamaIbu = binding.edtMotherReligion.text.toString()
        val kewarganegaraanIbu = binding.edtMotherNationality.text.toString()
        val pekerjaanIbu = binding.edtMotherOccupation.text.toString()
        val pendidikanIbu = binding.edtMotherEducation.text.toString()
        val pengeluaranIbu = binding.edtMotherExpenditure.text.toString()
        val alamatIbu = binding.edtMotherAddressAndPhone.text.toString()

        return DataIbu(
            namaIbu,
            tempatLahirIbu,
            tanggalLahirIbu,
            agamaIbu,
            kewarganegaraanIbu,
            pendidikanIbu,
            pekerjaanIbu,
            pengeluaranIbu,
            alamatIbu,
            statusIbu
        )
    }


    private fun getAyah(): DataAyah {
        val namaAyah = binding.edtFatherName.text.toString()
        val statusAyah = binding.spinnerFatherStatus.selectedItem.toString()
        var tanggalLahirAyah = binding.edtFatherBornDate.text.toString().takeIf { it.isNotEmpty() }
        val tempatLahirAyah = binding.edtFatherBornPlace.text.toString()

        binding.edtFatherBornDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    tanggalLahirAyah = dateFormat.format(selectedDate.time)
                    binding.edtFatherBornDate.setText(tanggalLahirAyah)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        val agamaAyah = binding.edtFatherReligion.text.toString()
        val kewarganegaraanAyah = binding.edtFatherNationality.text.toString()
        val pekerjaanAyah = binding.edtFatherOccupation.text.toString()
        val pendidikanAyah = binding.edtFatherEducation.text.toString()
        val pengeluaranAyah = binding.edtFatherExpenditure.text.toString()
        val alamatAyah = binding.edtFatherAddressAndPhone.text.toString()

        return DataAyah(
            namaAyah,
            tempatLahirAyah,
            tanggalLahirAyah,
            agamaAyah,
            kewarganegaraanAyah,
            pendidikanAyah,
            pekerjaanAyah,
            pengeluaranAyah,
            alamatAyah,
            statusAyah
        )
    }

    private fun getPendidikan(): DataPendidikan {
        val highschool = binding.edtHighschool.text.toString()
        val ijazah = binding.edtIjazahNumber.text.toString()
        val skhun = binding.edtSkhunNumber.text.toString()
        val lastEdu = binding.edtLastLongEdu.text.toString()
        val moveForm = binding.edtMoveFrom.text.toString()
        val reasonMove = binding.edtReasonMove.text.toString()
        val acceptIn = binding.edtAcceptIn.text.toString().toIntOrNull() ?: 0
        val acceptInSkill = binding.edtSkill.text.toString()
        val acceptIntSpecializeSkill = binding.edtSpecializeSkill.text.toString()
        val acceptInPackage = binding.edtPackageSkill.text.toString()

        var acceptDate = binding.edtDateAcceptIn.text.toString().takeIf { it.isNotEmpty() }

        return DataPendidikan(
            highschool,
            ijazah,
            skhun,
            lastEdu,
            moveForm,
            reasonMove,
            acceptIn,
            acceptInSkill,
            acceptIntSpecializeSkill,
            acceptInPackage,
            acceptDate
        )
    }

    private fun getKesehatan(): DataKesehatan {
        val bloodTypes = binding.spinnerBlood.selectedItem.toString()
        val deases = binding.edtDeases.text.toString()
        val abnormality = binding.edtAbnormality.text.toString()
        val heigth = binding.edtHeight.text.toString()
        val width = binding.edtWeight.text.toString()

        return DataKesehatan(
            gol_darah = bloodTypes,
            penyakit_pernah_diderita = deases,
            kelainan_jasmani = abnormality,
            tinggi = heigth,
            berat_badan =  width
        )
    }

    private fun getAlamat(): DataTempatTinggal {
        val address = binding.edtAddress.text.toString()
        val telephoneNumber = binding.edtTelephoneNumber.text.toString()
        val stayWith = binding.spinnerLiveWith.selectedItem.toString()
        val distance = binding.edtDistance.text.toString()

        return DataTempatTinggal(
            address,
            telephoneNumber,
            stayWith,
            distance
        )
    }

    private fun getBiodata(): DataDiri {
        val fullName = binding.edtFullName.text.toString()
        val shortName = binding.edtShortName.text.toString()
        val sex = binding.spinnerSex.selectedItem.toString()
        val bornPlace = binding.edtBornPlace.text.toString()
        var bornDate = binding.edtBornDate.text.toString().takeIf { it.isNotEmpty() }
        val religion = binding.edtReligion.text.toString()
        val nationality = binding.edtNationality.text.toString()
        val totalSiblings = binding.edtTotalSiblings.text.toString().toIntOrNull() ?: 0
        val adoptedSiblings = binding.edtAdoptedSiblings.text.toString().toIntOrNull() ?: 0
        val stepSiblings = binding.edtStepSiblings.text.toString().toIntOrNull() ?: 0
        val orderSiblings = binding.edtOrderSiblings.text.toString().toIntOrNull() ?: 0
        val parentInfo = binding.spinnerParent.selectedItem.toString()
        val language = binding.edtLanguage.text.toString()

        return DataDiri(
            nama_lengkap = fullName,
            nama_panggilan = shortName,
            jenis_kelamin = sex,
            tempat_lahir = bornPlace,
            agama = religion,
            kewarganegaraan = nationality,
            anak_ke = orderSiblings,
            jml_saudara_kandung = totalSiblings,
            jml_saudara_tiri = stepSiblings,
            jml_saudara_angkat = adoptedSiblings,
            kelengkapan_ortu = parentInfo,
            bahasa_sehari_hari = language,
            tanggal_lahir = bornDate
        )
    }

}