package com.project.virtualdatabooks.UI

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
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
import com.project.virtualdatabooks.Data.Request.StudentUpdateAyahRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateDataDiriRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateHobiRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateIbuRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateKesehatanRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdatePendidikanRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdatePerkembanganRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateSetelahPendidikanRequest
//import com.project.virtualdatabooks.Data.Request.StudentUpdateRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateTempatTinggalRequest
import com.project.virtualdatabooks.Data.Request.StudentUpdateWaliRequest
//import com.project.virtualdatabooks.Data.Request.TempatTinggal
//import com.project.virtualdatabooks.Data.Request.Wali
import com.project.virtualdatabooks.Data.ViewModel.StudentViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.StudentNotifyUpdate
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.FragmentEditFormStudentBinding
import java.text.SimpleDateFormat
import java.util.Locale

class EditFormStudentFragment : Fragment() {
    private lateinit var binding: FragmentEditFormStudentBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var tokenHandler: TokenHandler
    private var optionSelected: Int? = null

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

        val genderOptions = arrayOf("laki-laki", "perempuan")
        val sexAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)

        val parentOptions = arrayOf("yatim", "piatu", "yatim piatu", "lengkap")
        val parentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, parentOptions)

        val liveWithOptions = arrayOf("ortu", "saudara", "lainnya", "wali")
        val liveWithAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, liveWithOptions)

        val bloodTypeOptions = arrayOf("A", "B", "O", "AB")
        val bloodTypesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bloodTypeOptions)

        val fatherStatusOptions = arrayOf("masih hidup", "meninggal")
        val fatherStatusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fatherStatusOptions)

        val motherStatusOptions = arrayOf("masih hidup", "meninggal")
        val motherStatusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, motherStatusOptions)

        binding.spinnerSex.adapter = sexAdapter
        binding.spinnerOptionForm.adapter = adapter
        binding.spinnerParent.adapter = parentAdapter
        binding.spinnerLiveWith.adapter = liveWithAdapter
        binding.spinnerBlood.adapter = bloodTypesAdapter
        binding.spinnerFatherStatus.adapter = fatherStatusAdapter
        binding.spinnerMotherStatus.adapter = motherStatusAdapter

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

                optionSelected = position

                when (position) {
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

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                binding.sendButton.visibility = View.GONE
            }
        }

        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.sendButton.setOnClickListener {

            when (optionSelected) {
                1 -> {
                    // Update Biodata
                    val studentUpdateRequest = getBiodata()
                    studentViewModel.updateStudentBiodata(studentUpdateRequest)
                }
                2 -> {
                    // Update Tempat Tinggal
                    val studentUpdateRequestTempatTinggal = getAlamat()
                    studentViewModel.updateStudentTempatTinggal(studentUpdateRequestTempatTinggal)
                }
                3 -> {
                    // Update Kesehatan
                    val studentUpdateRequestKesehatan = getKesehatan()
                    studentViewModel.updateStudentKesehatan(studentUpdateRequestKesehatan)
                }
                4 -> {
                    // Update Pendidikan
                    val studentUpdateRequestPendidikan = getPendidikan()
                    studentViewModel.updateStudentPendidikan(studentUpdateRequestPendidikan)
                }
                5 -> {
                    // Update Ayah
                    val studentUpdateRequestAyah = getAyah()
                    studentViewModel.updateStudentAyah(studentUpdateRequestAyah)
                }
                6 -> {
                    // Update Ibu
                    val studentUpdateRequestIbu = getIbu()
                    studentViewModel.updateStudentIbu(studentUpdateRequestIbu)
                }
                7 -> {
                    // Update Wali
                    val studentUpdateRequestWali = getWali()
                    studentViewModel.updateStudentWali(studentUpdateRequestWali)
                }
                8 -> {
                    // Update Perkembangan Pendidikan
                    val studentUpdateRequestPerkembangan = getPerkembanganPendidikan()
                    studentViewModel.updateStudentPerkembangan(studentUpdateRequestPerkembangan)
                }
                9 -> {
                    // Update Setelah Pendidikan
                    val studentUpdateRequestSetelahPendidikan = getSetelahPendidikan()
                    studentViewModel.updateStudentSetelahPendidikan(studentUpdateRequestSetelahPendidikan)
                }
                10 -> {
                    // Update Hobi
                    val studentUpdateHobiRequest = getHobi()
                    studentViewModel.updateStudentHobi(studentUpdateHobiRequest)
                }
            }

        }

        studentViewModel.updateStudentBiodata.observe( viewLifecycleOwner, { response ->
                if (response.message != null){
                    val intent = Intent(requireContext(), StudentNotifyUpdate::class.java)
                    activity?.startActivity(intent)
                }
            }
        )

        return binding.root
    }

    private fun getHobi(): StudentUpdateHobiRequest {
        val kesenian = binding.edtKesenian.text.toString()
        val olahraga = binding.edtOlahraga.text.toString()
        val organisasi = binding.edtOrganisasi.text.toString()
        val lainLain = binding.edtLainLain.text.toString()

        return StudentUpdateHobiRequest(
            hobi_siswa = DataHobi(
                kesenian,
                olahraga,
                organisasi,
                lainLain
            )
        )
    }

    private fun getSetelahPendidikan(): StudentUpdateSetelahPendidikanRequest {
        val melanjutkanKe = binding.edtContinueTo.text.toString()
        val bekerjaDiPerusahaan = binding.edtWorkInCompany.text.toString()

        var bekerjaTanggalMulai = binding.edtStartWorkDate.text.toString()
        binding.edtStartWorkDate.setOnClickListener {
            showDatePicker()
        }

        val penghasilan = binding.edtIncome.text.toString()

        return StudentUpdateSetelahPendidikanRequest(
            setelah_pendidikan = DataSetelahPendidikan(
                melanjutkanKe,
                bekerjaDiPerusahaan,
                bekerjaTanggalMulai,
                penghasilan
            )
        )
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Format date as needed and set to the EditText
                val formattedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.edtStartWorkDate.setText(formattedDate)
            }, year, month, day)
        datePickerDialog.show()
    }


    private fun getPerkembanganPendidikan(): StudentUpdatePerkembanganRequest {
        val menerimaBeasiswaTahunKelasDari = binding.edtScholarshipReceived.text.toString()
        val meninggalkanSekolahIniAlasan = binding.edtLeftSchoolReason.text.toString()
        val akhirPendidikanTamatBelajarLulusTahun = binding.edtEducationEnd.text.toString()
        val akhirPendidikanNoTanggalIjazah = binding.edtEducationDiplomaNumber.text.toString()
        val akhirPendidikanNoTanggalSkhun = binding.edtEducationSkhunNumber.text.toString()

        var meninggalkanSekolahIniTanggal = binding.edtLeftSchool.text.toString()

        binding.edtLeftSchool.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    meninggalkanSekolahIniTanggal = dateFormat.format(selectedDate.time)
                    binding.edtLeftSchool.setText(meninggalkanSekolahIniTanggal)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        return StudentUpdatePerkembanganRequest(
            perkembangan = DataPerkembangan(
                menerimaBeasiswaTahunKelasDari,
                meninggalkanSekolahIniTanggal,
                meninggalkanSekolahIniAlasan,
                akhirPendidikanTamatBelajarLulusTahun,
                akhirPendidikanNoTanggalIjazah,
                akhirPendidikanNoTanggalSkhun
            )
        )
    }


    private fun getWali(): StudentUpdateWaliRequest {
        val namaWali = binding.edtWaliName.text.toString()

        var tanggalLahirWali = binding.edtWaliBornDate.text.toString()
        val tempatLahirWali = binding.edtWaliBornPlace.text.toString()

        binding.edtWaliBornDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    tanggalLahirWali = dateFormat.format(selectedDate.time)
                    binding.edtWaliBornDate.setText(tanggalLahirWali)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        val agamaWali = binding.edtWaliReligion.text.toString()
        val kewarganegaraanWali = binding.edtWaliNationality.text.toString()
        val pekerjaanWali = binding.edtWaliOccupation.text.toString()
        val pendidikanWali = binding.edtWaliEducation.text.toString()
        val pengeluaranWali = binding.edtWaliExpenditure.text.toString()
        val alamatWali = binding.edtWaliAddressAndPhone.text.toString()

        return StudentUpdateWaliRequest(
            wali = DataWali(
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
        )
    }


    private fun getIbu(): StudentUpdateIbuRequest {
        val namaIbu = binding.edtMotherName.text.toString()
        val statusIbu = binding.spinnerMotherStatus.selectedItem.toString()

        var tanggalLahirIbu = binding.edtMotherBornDate.text.toString()
        val tempatLahirIbu = binding.edtMotherBornPlace.text.toString()

        binding.edtMotherBornDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    tanggalLahirIbu = dateFormat.format(selectedDate.time)
                    binding.edtMotherBornDate.setText(tanggalLahirIbu)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        val agamaIbu = binding.edtMotherReligion.text.toString()
        val kewarganegaraanIbu = binding.edtMotherNationality.text.toString()
        val pekerjaanIbu = binding.edtMotherOccupation.text.toString()
        val pendidikanIbu = binding.edtMotherEducation.text.toString()
        val pengeluaranIbu = binding.edtMotherExpenditure.text.toString()
        val alamatIbu = binding.edtMotherAddressAndPhone.text.toString()

        return StudentUpdateIbuRequest(
            ibu_kandung = DataIbu(
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
        )
    }


    private fun getAyah(): StudentUpdateAyahRequest {
        val namaAyah = binding.edtFatherName.text.toString()
        val statusAyah = binding.spinnerFatherStatus.selectedItem.toString()

        var tanggalLahirAyah = binding.edtFatherBornDate.text.toString()
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

        return StudentUpdateAyahRequest(
            ayah_kandung = DataAyah(
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
        )
    }

    private fun getPendidikan(): StudentUpdatePendidikanRequest {
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

        var acceptDate = binding.edtDateAcceptIn.text.toString()

        binding.edtDateAcceptIn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    acceptDate = dateFormat.format(selectedDate.time)
                    binding.edtBornDate.setText(acceptDate)  // Update tampilan EditText
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        return StudentUpdatePendidikanRequest(
            pendidikan = DataPendidikan(
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
        )
    }

    private fun getKesehatan(): StudentUpdateKesehatanRequest {
        val bloodTypes = binding.spinnerBlood.selectedItem.toString()
        val deases = binding.edtDeases.text.toString()
        val abnormality = binding.edtAbnormality.text.toString()
        val heigth = binding.edtHeight.text.toString()
        val width = binding.edtWeight.text.toString()

        return StudentUpdateKesehatanRequest(
            kesehatan = DataKesehatan(
                gol_darah = bloodTypes,
                penyakit_pernah_diderita = deases,
                kelainan_jasmani = abnormality,
                tinggi = heigth,
                berat_badan =  width
            )
        )
    }

    private fun getAlamat(): StudentUpdateTempatTinggalRequest {
        val address = binding.edtAddress.text.toString()
        val telephoneNumber = binding.edtTelephoneNumber.text.toString()
        val stayWith = binding.spinnerLiveWith.selectedItem.toString()
        val distance = binding.edtDistance.text.toString()

        return StudentUpdateTempatTinggalRequest(
            tempat_tinggal = DataTempatTinggal(
                address,
                telephoneNumber,
                stayWith,
                distance
            )
        )
    }

    private fun getBiodata(): StudentUpdateDataDiriRequest {
        val fullName = binding.edtFullName.text.toString()
        val shortName = binding.edtShortName.text.toString()
        val sex = binding.spinnerSex.selectedItem.toString()
        val bornPlace = binding.edtBornPlace.text.toString()

        var bornDate = binding.edtBornDate.text.toString()

        binding.edtBornDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    bornDate = dateFormat.format(selectedDate.time)
                    binding.edtBornDate.setText(bornDate)  // Update tampilan EditText
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        val religion = binding.edtReligion.text.toString()
        val nationality = binding.edtNationality.text.toString()
        val totalSiblings = binding.edtTotalSiblings.text.toString().toIntOrNull() ?: 0
        val adoptedSiblings = binding.edtAdoptedSiblings.text.toString().toIntOrNull() ?: 0
        val stepSiblings = binding.edtStepSiblings.text.toString().toIntOrNull() ?: 0
        val orderSiblings = binding.edtOrderSiblings.text.toString().toIntOrNull() ?: 0
        val parentInfo = binding.spinnerParent.selectedItem.toString()
        val language = binding.edtLanguage.text.toString()

        return StudentUpdateDataDiriRequest(
            data_diri = DataDiri(
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
        )
    }

}