package com.project.virtualdatabooks.UI

import android.content.Intent
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
    private var optionSelected: Int? = null
    private var userId:Int? = null

    private val genderOptions = arrayOf("laki-laki", "perempuan")
    private val parentOptions = arrayOf("yatim", "piatu", "yatim piatu", "lengkap")
    private val liveWithOptions = arrayOf("ortu", "saudara", "lainnya", "wali")
    private val bloodTypeOptions = arrayOf("A", "B", "O", "AB")
    private val fatherStatusOptions = arrayOf("masih hidup", "meninggal")
    private val motherStatusOptions = arrayOf("masih hidup", "meninggal")

    private var previousDataDiri: DataDiri? = null
    private var previousPerkembangan: DataPerkembangan? = null
    private var previousAyahKandung: DataAyah? = null
    private var previousIbuKandung: DataIbu? = null
    private var previousKesehatan: DataKesehatan? = null
    private var previousPendidikan: DataPendidikan? = null
    private var previousSetelahPendidikan: DataSetelahPendidikan? = null
    private var previousTempatTinggal: DataTempatTinggal? = null
    private var previousWali: DataWali? = null
    private var previousHobiSiswa: DataHobi? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository, requireContext())
        studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)


        userId = arguments?.getInt("USER_ID")
        userId?.let { studentViewModel.getStudentBiodata(it) }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditFormStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        studentViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        })

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
    }

    private fun fillInForm(){
        studentViewModel.studentBiodata.observe(viewLifecycleOwner, {data ->
            data?.dataDiri?.let { biodata ->

                previousDataDiri = DataDiri(
                    nama_lengkap = biodata.namaLengkap,
                    nama_panggilan = biodata.namaPanggilan,
                    jenis_kelamin = biodata.jenisKelamin,
                    tempat_lahir = biodata.tempatLahir,
                    agama = biodata.agama,
                    kewarganegaraan = biodata.kewarganegaraan,
                    anak_ke = biodata.anakKe,
                    jml_saudara_kandung = biodata.jmlSaudaraKandung,
                    jml_saudara_tiri = biodata.jmlSaudaraTiri,
                    jml_saudara_angkat = biodata.jmlSaudaraAngkat,
                    kelengkapan_ortu = biodata.kelengkapanOrtu,
                    bahasa_sehari_hari = biodata.bahasaSehariHari,
                    tanggal_lahir = biodata.tanggalLahir
                )

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
                previousTempatTinggal = DataTempatTinggal(
                    alamat = tempatTinggal.alamat,
                    no_telepon = tempatTinggal.noTelepon,
                    tinggal_dengan = tempatTinggal.tinggalDengan,
                    jarak_ke_sekolah = tempatTinggal.jarakKeSekolah
                )

                binding.edtAddress.setText(tempatTinggal.alamat)
                binding.edtTelephoneNumber.setText(tempatTinggal.noTelepon)
                binding.edtDistance.setText(tempatTinggal.jarakKeSekolah)

                val stayWithPosition = liveWithOptions.indexOf(tempatTinggal.tinggalDengan)
                binding.spinnerLiveWith.setSelection(stayWithPosition)
            }

            data?.pendidikan?.let { pendidikan ->
                previousPendidikan = DataPendidikan(
                    sebelumnya_tamatan_dari = pendidikan.sebelumnyaTamatanDari,
                    sebelumnya_tanggal_dan_ijazah = pendidikan.sebelumnyaTanggalDanIjazah,
                    sebelumnya_tanggal_skhun_dan_ = pendidikan.sebelumnyaTanggalSkhunDan,
                    sebelumnya_lama_belajar = pendidikan.sebelumnyaLamaBelajar,
                    pindahan_dari_sekolah = pendidikan.pindahanDariSekolah,
                    pindahan_alasan = pendidikan.pindahanAlasan,
                    diterima_di_kelas = pendidikan.diterimaDiKelas,
                    diterima_di_bidang_keahlian = pendidikan.diterimaDiBidangKeahlian,
                    diterima_di_program_keahlian = pendidikan.diterimaDiProgramKeahlian,
                    diterima_di_paket_keahlian = pendidikan.diterimaDiPaketKeahlian,
                    diterima_tanggal = pendidikan.diterimaTanggal
                )

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
                previousAyahKandung = DataAyah(
                    nama = ayahKandung.nama,
                    tempat_lahir = ayahKandung.tempatLahir,
                    tanggal_lahir = ayahKandung.tanggalLahir,
                    agama = ayahKandung.agama,
                    kewarganegaraan = ayahKandung.kewarganegaraan,
                    pendidikan = ayahKandung.pendidikan,
                    pekerjaan = ayahKandung.pekerjaan,
                    pengeluaran_per_bulan = ayahKandung.pengeluaranPerBulan,
                    alamat_dan_no_telepon = ayahKandung.alamatDanNoTelepon,
                    status = ayahKandung.status
                )

                binding.edtFatherName.setText(ayahKandung.nama)
                binding.edtFatherReligion.setText(ayahKandung.agama)
                binding.edtFatherNationality.setText(ayahKandung.kewarganegaraan)
                binding.edtFatherOccupation.setText(ayahKandung.pekerjaan)
                binding.edtFatherEducation.setText(ayahKandung.pendidikan)
                binding.edtFatherExpenditure.setText(ayahKandung.pengeluaranPerBulan)
                binding.edtFatherAddressAndPhone.setText(ayahKandung.alamatDanNoTelepon)
                binding.edtFatherBornDate.setText(ayahKandung.tanggalLahir)
                binding.edtFatherBornPlace.setText(ayahKandung.tempatLahir)

                val fatherPosition = fatherStatusOptions.indexOf(ayahKandung.status)
                binding.spinnerFatherStatus.setSelection(fatherPosition)
            }

            data?.ibuKandung?.let { ibuKandung ->
                previousIbuKandung = DataIbu(
                    nama = ibuKandung.nama,
                    tempat_lahir = ibuKandung.tempatLahir,
                    tanggal_lahir = ibuKandung.tanggalLahir,
                    agama = ibuKandung.agama,
                    kewarganegaraan = ibuKandung.kewarganegaraan,
                    pendidikan = ibuKandung.pendidikan,
                    pekerjaan = ibuKandung.pekerjaan,
                    pengeluaran_per_bulan = ibuKandung.pengeluaranPerBulan,
                    alamat_dan_no_telepon = ibuKandung.alamatDanNoTelepon,
                    status = ibuKandung.status
                )

                binding.edtMotherName.setText(ibuKandung.nama)
                binding.edtMotherReligion.setText(ibuKandung.agama)
                binding.edtMotherNationality.setText(ibuKandung.kewarganegaraan)
                binding.edtMotherOccupation.setText(ibuKandung.pekerjaan)
                binding.edtMotherEducation.setText(ibuKandung.pendidikan )
                binding.edtMotherExpenditure.setText(ibuKandung.pengeluaranPerBulan)
                binding.edtMotherAddressAndPhone.setText(ibuKandung.alamatDanNoTelepon)
                binding.edtMotherBornDate.setText(ibuKandung.tanggalLahir)
                binding.edtMotherBornPlace.setText(ibuKandung.tempatLahir)

                val motherPosition = motherStatusOptions.indexOf(ibuKandung.status)
                binding.spinnerMotherStatus.setSelection(motherPosition)
            }

            data?.wali?.let { wali ->
                previousWali = DataWali(
                    nama = wali.nama,
                    tempat_lahir = wali.tempatLahir,
                    tanggal_lahir = wali.tanggalLahir,
                    agama = wali.agama,
                    kewarganegaraan = wali.kewarganegaraan,
                    pendidikan = wali.pendidikan,
                    pekerjaan = wali.pekerjaan,
                    pengeluaran_per_bulan = wali.pengeluaranPerBulan,
                    alamat_dan_no_telepon = wali.alamatDanNoTelepon
                )

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
                previousSetelahPendidikan = DataSetelahPendidikan(
                    melanjutkan_ke = setelahPendidikan.melanjutkanKe,
                    bekerja_nama_perusahaan = setelahPendidikan.bekerjaNamaPerusahaan,
                    bekerja_tanggal_mulai = setelahPendidikan.bekerjaTanggalMulai,
                    bekerja_penghasilan = setelahPendidikan.bekerjaPenghasilan
                )

                binding.edtContinueTo.setText(setelahPendidikan.melanjutkanKe)
                binding.edtWorkInCompany.setText(setelahPendidikan.bekerjaNamaPerusahaan)
                binding.edtStartWorkDate.setText(setelahPendidikan.bekerjaTanggalMulai)
                binding.edtIncome.setText(setelahPendidikan.bekerjaPenghasilan)
            }

            data?.kesehatan?.let { kesehatan ->
                previousKesehatan = DataKesehatan(
                    gol_darah = kesehatan.golDarah,
                    penyakit_pernah_diderita = kesehatan.penyakitPernahDiderita,
                    kelainan_jasmani = kesehatan.kelainanJasmani,
                    tinggi = kesehatan.tinggi,
                    berat_badan = kesehatan.beratBadan
                )

                binding.edtDeases.setText(kesehatan.penyakitPernahDiderita)
                binding.edtAbnormality.setText(kesehatan.kelainanJasmani)
                binding.edtHeight.setText(kesehatan.tinggi)
                binding.edtWeight.setText(kesehatan.beratBadan)

                val bloodTypesPosition = bloodTypeOptions.indexOf(kesehatan.golDarah)
                binding.spinnerBlood.setSelection(bloodTypesPosition)
            }

            data?.hobiSiswa?.let { hobiSiswa ->
                previousHobiSiswa = DataHobi(
                    kesenian = hobiSiswa.kesenian,
                    olahraga = hobiSiswa.olahraga,
                    organisasi = hobiSiswa.organisasi,
                    lain_lain = hobiSiswa.lainLain
                )

                binding.edtKesenian.setText(hobiSiswa.kesenian)
                binding.edtOlahraga.setText(hobiSiswa.olahraga)
                binding.edtOrganisasi.setText(hobiSiswa.organisasi)
                binding.edtLainLain.setText(hobiSiswa.lainLain)
            }

            data?.perkembangan?.let { perkembangan ->
                previousPerkembangan = DataPerkembangan(
                    menerima_beasiswa_tahun_kelas_dari = perkembangan.menerimaBeaSiswaTahunKelasDari,
                    meninggalkan_sekolah_ini_tanggal = perkembangan.meninggalkanSekolahIniTanggal,
                    meninggalkan_sekolah_ini_alasan = perkembangan.meninggalkanSekolahIniAlasan,
                    akhir_pendidikan_tamat_belajar_lulus_tahun = perkembangan.akhirPendidikanTamatBelajarLulusTahun,
                    akhir_pendidikan_no_tanggal_ijazah = perkembangan.akhirPendidikanNoTanggalIjazah,
                    akhir_pendidikan_no_tanggal_skhun = perkembangan.akhirPendidikanNoTanggalSkhun
                )
                binding.edtScholarshipReceived.setText(perkembangan.menerimaBeaSiswaTahunKelasDari)
                binding.edtLeftSchool.setText(perkembangan.meninggalkanSekolahIniTanggal)
                binding.edtLeftSchoolReason.setText(perkembangan.meninggalkanSekolahIniAlasan)
                binding.edtEducationEnd.setText(perkembangan.akhirPendidikanTamatBelajarLulusTahun)
                binding.edtEducationDiplomaNumber.setText(perkembangan.akhirPendidikanNoTanggalIjazah)
                binding.edtEducationSkhunNumber.setText(perkembangan.akhirPendidikanNoTanggalSkhun)
            }
        })
    }

    private fun getHobi(): DataHobi? {
        val kesenian = binding.edtKesenian.text.toString().takeIf { it.isNotEmpty() }
        val olahraga = binding.edtOlahraga.text.toString().takeIf { it.isNotEmpty() }
        val organisasi = binding.edtOrganisasi.text.toString().takeIf { it.isNotEmpty() }
        val lainLain = binding.edtLainLain.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = kesenian != previousHobiSiswa?.kesenian ||
                olahraga != previousHobiSiswa?.olahraga ||
                organisasi != previousHobiSiswa?.organisasi ||
                lainLain != previousHobiSiswa?.lain_lain

        return if (isUpdated) {
            DataHobi(
                kesenian,
                olahraga,
                organisasi,
                lainLain
            )
        } else {
            null
        }
    }

    private fun getSetelahPendidikan(): DataSetelahPendidikan? {
        val melanjutkanKe = binding.edtContinueTo.text.toString().takeIf { it.isNotEmpty() }
        val bekerjaDiPerusahaan = binding.edtWorkInCompany.text.toString().takeIf { it.isNotEmpty() }
        val bekerjaTanggalMulai = binding.edtStartWorkDate.text.toString().takeIf { it.isNotEmpty() }
        val penghasilan = binding.edtIncome.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = melanjutkanKe != previousSetelahPendidikan?.melanjutkan_ke ||
                bekerjaDiPerusahaan != previousSetelahPendidikan?.bekerja_nama_perusahaan ||
                bekerjaTanggalMulai != previousSetelahPendidikan?.bekerja_tanggal_mulai ||
                penghasilan != previousSetelahPendidikan?.bekerja_penghasilan

        return if (isUpdated) {
            DataSetelahPendidikan(
                melanjutkanKe,
                bekerjaDiPerusahaan,
                bekerjaTanggalMulai,
                penghasilan
            )
        } else {
            null
        }
    }

    private fun getPerkembanganPendidikan(): DataPerkembangan? {
        val menerimaBeasiswaTahunKelasDari = binding.edtScholarshipReceived.text.toString().takeIf { it.isNotEmpty() }
        val meninggalkanSekolahIniAlasan = binding.edtLeftSchoolReason.text.toString().takeIf { it.isNotEmpty() }
        val akhirPendidikanTamatBelajarLulusTahun = binding.edtEducationEnd.text.toString().takeIf { it.isNotEmpty() }
        val akhirPendidikanNoTanggalIjazah = binding.edtEducationDiplomaNumber.text.toString().takeIf { it.isNotEmpty() }
        val akhirPendidikanNoTanggalSkhun = binding.edtEducationSkhunNumber.text.toString().takeIf { it.isNotEmpty() }
        val meninggalkanSekolahIniTanggal = binding.edtLeftSchool.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = menerimaBeasiswaTahunKelasDari != previousPerkembangan?.menerima_beasiswa_tahun_kelas_dari ||
                meninggalkanSekolahIniTanggal != previousPerkembangan?.meninggalkan_sekolah_ini_tanggal ||
                meninggalkanSekolahIniAlasan != previousPerkembangan?.meninggalkan_sekolah_ini_alasan ||
                akhirPendidikanTamatBelajarLulusTahun != previousPerkembangan?.akhir_pendidikan_tamat_belajar_lulus_tahun ||
                akhirPendidikanNoTanggalIjazah != previousPerkembangan?.akhir_pendidikan_no_tanggal_ijazah ||
                akhirPendidikanNoTanggalSkhun != previousPerkembangan?.akhir_pendidikan_no_tanggal_skhun

        return if (isUpdated) {
            DataPerkembangan(
                menerimaBeasiswaTahunKelasDari,
                meninggalkanSekolahIniTanggal,
                meninggalkanSekolahIniAlasan,
                akhirPendidikanTamatBelajarLulusTahun,
                akhirPendidikanNoTanggalIjazah,
                akhirPendidikanNoTanggalSkhun
            )
        } else {
            null
        }
    }


    private fun getWali(): DataWali? {
        val namaWali = binding.edtWaliName.text.toString().takeIf { it.isNotEmpty() }
        val tanggalLahirWali = binding.edtWaliBornDate.text.toString().takeIf { it.isNotEmpty() }
        val tempatLahirWali = binding.edtWaliBornPlace.text.toString().takeIf { it.isNotEmpty() }
        val agamaWali = binding.edtWaliReligion.text.toString().takeIf { it.isNotEmpty() }
        val kewarganegaraanWali = binding.edtWaliNationality.text.toString().takeIf { it.isNotEmpty() }
        val pekerjaanWali = binding.edtWaliOccupation.text.toString().takeIf { it.isNotEmpty() }
        val pendidikanWali = binding.edtWaliEducation.text.toString().takeIf { it.isNotEmpty() }
        val pengeluaranWali = binding.edtWaliExpenditure.text.toString().takeIf { it.isNotEmpty() }
        val alamatWali = binding.edtWaliAddressAndPhone.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = namaWali != previousWali?.nama ||
                tanggalLahirWali != previousWali?.tanggal_lahir ||
                tempatLahirWali != previousWali?.tempat_lahir ||
                agamaWali != previousWali?.agama ||
                kewarganegaraanWali != previousWali?.kewarganegaraan ||
                pekerjaanWali != previousWali?.pekerjaan ||
                pendidikanWali != previousWali?.pendidikan ||
                pengeluaranWali != previousWali?.pengeluaran_per_bulan ||
                alamatWali != previousWali?.alamat_dan_no_telepon

        return if (isUpdated) {
            DataWali(
                namaWali,
                tempatLahirWali,
                tanggalLahirWali,
                agamaWali,
                kewarganegaraanWali,
                pendidikanWali,
                pekerjaanWali,
                pengeluaranWali,
                alamatWali
            )
        } else {
            null
        }
    }


    private fun getIbu(): DataIbu? {
        val namaIbu = binding.edtMotherName.text.toString().takeIf { it.isNotEmpty() }
        val statusIbu = binding.spinnerMotherStatus.selectedItem.toString().takeIf { it.isNotEmpty() }
        val tanggalLahirIbu = binding.edtMotherBornDate.text.toString().takeIf { it.isNotEmpty() }
        val tempatLahirIbu = binding.edtMotherBornPlace.text.toString().takeIf { it.isNotEmpty() }
        val agamaIbu = binding.edtMotherReligion.text.toString().takeIf { it.isNotEmpty() }
        val kewarganegaraanIbu = binding.edtMotherNationality.text.toString().takeIf { it.isNotEmpty() }
        val pekerjaanIbu = binding.edtMotherOccupation.text.toString().takeIf { it.isNotEmpty() }
        val pendidikanIbu = binding.edtMotherEducation.text.toString().takeIf { it.isNotEmpty() }
        val pengeluaranIbu = binding.edtMotherExpenditure.text.toString().takeIf { it.isNotEmpty() }
        val alamatIbu = binding.edtMotherAddressAndPhone.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = namaIbu != previousIbuKandung?.nama ||
                statusIbu != previousIbuKandung?.status ||
                tanggalLahirIbu != previousIbuKandung?.tanggal_lahir ||
                tempatLahirIbu != previousIbuKandung?.tempat_lahir ||
                agamaIbu != previousIbuKandung?.agama ||
                kewarganegaraanIbu != previousIbuKandung?.kewarganegaraan ||
                pekerjaanIbu != previousIbuKandung?.pekerjaan ||
                pendidikanIbu != previousIbuKandung?.pendidikan ||
                pengeluaranIbu != previousIbuKandung?.pengeluaran_per_bulan ||
                alamatIbu != previousIbuKandung?.alamat_dan_no_telepon

        return if (isUpdated) {
            DataIbu(
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
        } else {
            null
        }
    }


    private fun getAyah(): DataAyah? {
        val namaAyah = binding.edtFatherName.text.toString().takeIf { it.isNotEmpty() }
        val statusAyah = binding.spinnerFatherStatus.selectedItem.toString().takeIf { it.isNotEmpty() }
        val tanggalLahirAyah = binding.edtFatherBornDate.text.toString().takeIf { it.isNotEmpty() }
        val tempatLahirAyah = binding.edtFatherBornPlace.text.toString().takeIf { it.isNotEmpty() }
        val agamaAyah = binding.edtFatherReligion.text.toString().takeIf { it.isNotEmpty() }
        val kewarganegaraanAyah = binding.edtFatherNationality.text.toString().takeIf { it.isNotEmpty() }
        val pekerjaanAyah = binding.edtFatherOccupation.text.toString().takeIf { it.isNotEmpty() }
        val pendidikanAyah = binding.edtFatherEducation.text.toString().takeIf { it.isNotEmpty() }
        val pengeluaranAyah = binding.edtFatherExpenditure.text.toString().takeIf { it.isNotEmpty() }
        val alamatAyah = binding.edtFatherAddressAndPhone.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = namaAyah != previousAyahKandung?.nama ||
                statusAyah != previousAyahKandung?.status ||
                tanggalLahirAyah != previousAyahKandung?.tanggal_lahir ||
                tempatLahirAyah != previousAyahKandung?.tempat_lahir ||
                agamaAyah != previousAyahKandung?.agama ||
                kewarganegaraanAyah != previousAyahKandung?.kewarganegaraan ||
                pekerjaanAyah != previousAyahKandung?.pekerjaan ||
                pendidikanAyah != previousAyahKandung?.pendidikan ||
                pengeluaranAyah != previousAyahKandung?.pengeluaran_per_bulan ||
                alamatAyah != previousAyahKandung?.alamat_dan_no_telepon

        return if (isUpdated) {
            DataAyah(
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
        } else {
            null
        }

    }

    private fun getPendidikan(): DataPendidikan? {
        val highschool = binding.edtHighschool.text.toString().takeIf { it.isNotEmpty() }
        val ijazah = binding.edtIjazahNumber.text.toString().takeIf { it.isNotEmpty() }
        val skhun = binding.edtSkhunNumber.text.toString().takeIf { it.isNotEmpty() }
        val lastEdu = binding.edtLastLongEdu.text.toString().takeIf { it.isNotEmpty() }
        val moveForm = binding.edtMoveFrom.text.toString().takeIf { it.isNotEmpty() }
        val reasonMove = binding.edtReasonMove.text.toString().takeIf { it.isNotEmpty() }
        val acceptIn = binding.edtAcceptIn.text.toString().toIntOrNull() ?: 0
        val acceptInSkill = binding.edtSkill.text.toString().takeIf { it.isNotEmpty() }
        val acceptIntSpecializeSkill = binding.edtSpecializeSkill.text.toString().takeIf { it.isNotEmpty() }
        val acceptInPackage = binding.edtPackageSkill.text.toString().takeIf { it.isNotEmpty() }
        val acceptDate = binding.edtDateAcceptIn.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = highschool != previousPendidikan?.sebelumnya_tamatan_dari ||
                ijazah != previousPendidikan?.sebelumnya_tanggal_dan_ijazah ||
                skhun != previousPendidikan?.sebelumnya_tanggal_skhun_dan_ ||
                lastEdu != previousPendidikan?.sebelumnya_lama_belajar ||
                moveForm != previousPendidikan?.pindahan_dari_sekolah ||
                reasonMove != previousPendidikan?.pindahan_alasan ||
                acceptIn != previousPendidikan?.diterima_di_kelas ||
                acceptInSkill != previousPendidikan?.diterima_di_bidang_keahlian ||
                acceptIntSpecializeSkill != previousPendidikan?.diterima_di_program_keahlian ||
                acceptInPackage != previousPendidikan?.diterima_di_paket_keahlian ||
                acceptDate != previousPendidikan?.diterima_tanggal

        return if (isUpdated) {
             DataPendidikan(
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
        } else {
            null
        }
    }

    private fun getKesehatan(): DataKesehatan? {
        val bloodTypes = binding.spinnerBlood.selectedItem.toString().takeIf { it.isNotEmpty() }
        val deases = binding.edtDeases.text.toString().takeIf { it.isNotEmpty() }
        val abnormality = binding.edtAbnormality.text.toString().takeIf { it.isNotEmpty() }
        val heigth = binding.edtHeight.text.toString().takeIf { it.isNotEmpty() }
        val width = binding.edtWeight.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = bloodTypes != previousKesehatan?.gol_darah ||
                deases != previousKesehatan?.penyakit_pernah_diderita ||
                abnormality != previousKesehatan?.kelainan_jasmani ||
                heigth != previousKesehatan?.tinggi ||
                width != previousKesehatan?.berat_badan

        return if (isUpdated) {
            DataKesehatan(
                bloodTypes,
                deases,
                abnormality,
                heigth,
                width
            )
        } else {
            null
        }
    }

    private fun getAlamat(): DataTempatTinggal? {
        val address = binding.edtAddress.text.toString().takeIf { it.isNotEmpty() }
        val telephoneNumber = binding.edtTelephoneNumber.text.toString().takeIf { it.isNotEmpty() }
        val stayWith = binding.spinnerLiveWith.selectedItem.toString().takeIf { it.isNotEmpty() }
        val distance = binding.edtDistance.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = address != previousTempatTinggal?.alamat ||
                telephoneNumber != previousTempatTinggal?.no_telepon ||
                stayWith != previousTempatTinggal?.tinggal_dengan ||
                distance != previousTempatTinggal?.jarak_ke_sekolah

        return if (isUpdated) {
            DataTempatTinggal(
                address,
                telephoneNumber,
                stayWith,
                distance
            )
        } else {
            null
        }
    }

    private fun getBiodata(): DataDiri? {
        val fullName = binding.edtFullName.text.toString().takeIf { it.isNotEmpty() }
        val shortName = binding.edtShortName.text.toString().takeIf { it.isNotEmpty() }
        val sex = binding.spinnerSex.selectedItem.toString().takeIf { it.isNotEmpty() }
        val bornPlace = binding.edtBornPlace.text.toString().takeIf { it.isNotEmpty() }
        val bornDate = binding.edtBornDate.text.toString().takeIf { it.isNotEmpty() }
        val religion = binding.edtReligion.text.toString().takeIf { it.isNotEmpty() }
        val nationality = binding.edtNationality.text.toString().takeIf { it.isNotEmpty() }
        val totalSiblings = binding.edtTotalSiblings.text.toString().toIntOrNull() ?: 0
        val adoptedSiblings = binding.edtAdoptedSiblings.text.toString().toIntOrNull() ?: 0
        val stepSiblings = binding.edtStepSiblings.text.toString().toIntOrNull() ?: 0
        val orderSiblings = binding.edtOrderSiblings.text.toString().toIntOrNull() ?: 0
        val parentInfo = binding.spinnerParent.selectedItem.toString().takeIf { it.isNotEmpty() }
        val language = binding.edtLanguage.text.toString().takeIf { it.isNotEmpty() }

        val isUpdated = fullName != previousDataDiri?.nama_lengkap ||
                shortName != previousDataDiri?.nama_panggilan ||
                sex != previousDataDiri?.jenis_kelamin ||
                bornPlace != previousDataDiri?.tempat_lahir ||
                bornDate != previousDataDiri?.tanggal_lahir ||
                religion != previousDataDiri?.agama ||
                nationality != previousDataDiri?.kewarganegaraan ||
                orderSiblings != previousDataDiri?.anak_ke ||
                totalSiblings != previousDataDiri?.jml_saudara_kandung ||
                stepSiblings != previousDataDiri?.jml_saudara_tiri ||
                adoptedSiblings != previousDataDiri?.jml_saudara_angkat ||
                parentInfo != previousDataDiri?.kelengkapan_ortu ||
                language != previousDataDiri?.bahasa_sehari_hari

        return if (isUpdated) {
            DataDiri(
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
        } else {
            null
        }
    }

}