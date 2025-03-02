package com.project.virtualdatabooks.UI

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.project.virtualdatabooks.Data.Repository.Repository
import com.project.virtualdatabooks.Data.ViewModel.StudentViewModel
import com.project.virtualdatabooks.Data.ViewModelFactory.ViewModelFactory
import com.project.virtualdatabooks.Network.ApiConfig
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.Support.TokenHandler
import com.project.virtualdatabooks.databinding.CustomSnackbarBinding
import com.project.virtualdatabooks.databinding.FragmentBiodataBinding


class BiodataFragment : Fragment() {
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var tokenHandler: TokenHandler
    private lateinit var binding: FragmentBiodataBinding

    private var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenHandler = TokenHandler(requireContext())
        val token = tokenHandler.getToken() ?: ""

        val repository = Repository(ApiConfig.getApiService(token))
        val factory = ViewModelFactory(repository, requireContext())
        studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        userId = arguments?.getInt("USER_ID")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId?.let { studentViewModel.getStudentBiodata(it) }

        studentViewModel.studentBiodata.observe(viewLifecycleOwner, { data ->
            //Header
            binding.ProfilName.text = data.dataDiri?.namaPanggilan
            binding.ProfilClass.text = data.jurusan?.nama

            //Biodata
            binding.tvFullName.text = "Nama Lengkap : ${data.dataDiri?.namaLengkap ?: "Tidak ada data"}"
            binding.tvSex.text = "Jenis Kelamin : ${data.dataDiri?.jenisKelamin ?: "Tidak ada data"}"
            binding.tvBorn.text = "Tempat Lahir : ${data.dataDiri?.tempatLahir ?: "Tidak ada data"}"
            binding.tvBornDate.text = "Tanggal Lahir : ${data.dataDiri?.tanggalLahir ?: "Tidak ada data"}"
            binding.tvReligion.text = "Agama : ${data.dataDiri?.agama ?: "Tidak ada data"}"
            binding.tvNationality.text = "Kewarganegaraan : ${data.dataDiri?.kewarganegaraan ?: "Tidak ada data"}"

            //Alamat
            binding.tvAddress.text = "Alamat : ${data.tempatTinggal?.alamat ?: "Tidak ada data"}"
            binding.tvTelephoneNumber.text = "No Telephone : ${data.tempatTinggal?.noTelepon ?: "Tidak ada data"}"
            binding.tvLiveWith.text = "Tinggal Dengan : ${data.tempatTinggal?.tinggalDengan ?: "Tidak ada data"}"
            binding.tvDistance.text = "Jarak Ke Sekolah : ${data.tempatTinggal?.jarakKeSekolah ?: "Tidak ada data"}"

            //Kesehatan
            binding.tvBloodType.text = "Gol. Darah : ${data.kesehatan?.golDarah ?: "Tidak ada data"}"
            binding.tvDeases.text = "Penyakit : ${data.kesehatan?.penyakitPernahDiderita ?: "Tidak ada data"}"
            binding.tvAbnormality.text = "Kelainan Jasmani : ${data.kesehatan?.kelainanJasmani ?: "Tidak ada data"}"
            binding.tvHeight.text = "Tinggi Badan : ${data.kesehatan?.tinggi ?: "Tidak ada data"}"
            binding.tvWeight.text = "Berat Badan : ${data.kesehatan?.golDarah ?: "Tidak ada data"}"

            //Pendidikan
            binding.tvHighschool.text = "Tamatan Dari : ${data.pendidikan?.sebelumnyaTamatanDari ?: "Tidak ada data"}"
            binding.tvIjazahNumber.text = "No Ijazah : ${data.pendidikan?.sebelumnyaTanggalDanIjazah ?: "Tidak ada data"}" //gaada nomor ijazah dr api
            binding.tvSkhunNumber.text = "No SKHUN : ${data.pendidikan?.sebelumnyaTanggalSkhunDan ?: "Tidak ada data"}"
            binding.tvMoveFrom.text = "Pindahan Dari : ${data.pendidikan?.pindahanDariSekolah ?: "Tidak ada data"}"
            binding.tvReasonMove.text = "Alasan Dipindahkan : ${data.pendidikan?.pindahanAlasan ?: "Tidak ada data"}"
            binding.tvAcceptIn.text = "Diterima di Kelas : ${data.pendidikan?.diterimaDiKelas ?: "Tidak ada data"}"
            binding.tvSkill.text = "Bidang Keahlian : ${data.pendidikan?.diterimaDiBidangKeahlian ?: "Tidak ada data"}"
            binding.tvSpecializeSkill.text = "Program Keahlian : ${data.pendidikan?.diterimaDiProgramKeahlian ?: "Tidak ada data"}"
            binding.tvPackageSkill.text = "Paket Keahlian : ${data.pendidikan?.diterimaDiPaketKeahlian ?: "Tidak ada data"}"
            binding.tvDateAcceptIn.text = "Tanggal Diterima : ${data.pendidikan?.diterimaTanggal ?: "Tidak ada data"}"

            //Keterangan Ayah
            binding.tvFatherName.text = "Nama : ${data.ayahKandung?.nama ?: "Tidak ada data"}"
            binding.tvFatherDob.text = "TTL : ${data.ayahKandung?.tempatLahir ?: "Tidak ada data"}, ${data.ayahKandung?.tanggalLahir ?: "Tidak ada data"}"
            binding.tvFatherReligion.text = "Agama : ${data.ayahKandung?.agama ?: "Tidak ada data"}"
            binding.tvFatherNationality.text = "Kewarganegaraan : ${data.ayahKandung?.kewarganegaraan ?: "Tidak ada data"}"
            binding.tvFatherEducation.text = "Riwayat Pendidikan : ${data.ayahKandung?.pendidikan ?: "Tidak ada data"}"
            binding.tvFatherOccupation.text = "Pekerjaan : ${data.ayahKandung?.pekerjaan ?: "Tidak ada data"}"
            binding.tvFatherExpenditure.text = "Pengeluaran per Bulan : ${data.ayahKandung?.pengeluaranPerBulan ?: "Tidak ada data"}"
            binding.tvFatherAddressAndPhone.text = "Alamat dan Telp : ${data.ayahKandung?.alamatDanNoTelepon ?: "Tidak ada data"}"
            binding.tvFatherStatus.text = "Masih Hidup/Meninggal Dunia : ${data.ayahKandung?.status ?: "Tidak ada data"}"

            //Keterangan Ibu
            binding.tvMotherName.text = "Nama : ${data.ibuKandung?.nama ?: "Tidak ada data"}"
            binding.tvMotherDob.text = "TTL : ${data.ibuKandung?.tempatLahir}, ${data.ibuKandung?.tanggalLahir ?: "Tidak ada data"}"
            binding.tvMotherReligion.text = "Agama : ${data.ibuKandung?.agama ?: "Tidak ada data"}"
            binding.tvMotherNationality.text = "Kewarganegaraan : ${data.ibuKandung?.kewarganegaraan ?: "Tidak ada data"}"
            binding.tvMotherEducation.text = "Riwayat Pendidikan : ${data.ibuKandung?.pendidikan ?: "Tidak ada data"}"
            binding.tvMotherOccupation.text = "Pekerjaan : ${data.ibuKandung?.pekerjaan ?: "Tidak ada data"}"
            binding.tvMotherExpenditure.text = "Pengeluaran per Bulan : ${data.ibuKandung?.pengeluaranPerBulan ?: "Tidak ada data"}"
            binding.tvMotherAddressAndPhone.text = "Alamat dan Telp : ${data.ibuKandung?.alamatDanNoTelepon ?: "Tidak ada data"}"
            binding.tvMotherStatus.text = "Masih Hidup/Meninggal Dunia : ${data.ibuKandung?.status ?: "Tidak ada data"}"

            //Keterangan Wali
            binding.tvGuardianName.text = "Nama : ${data.wali?.nama ?: "Tidak ada data"}"
            binding.tvGuardianDob.text = "TTL : ${data.wali?.tempatLahir ?: "Tidak ada data"}, ${data.wali?.tanggalLahir ?: "Tidak ada data"}"
            binding.tvGuardianReligion.text = "Agama : ${data.wali?.agama ?: "Tidak ada data"}"
            binding.tvGuardianNationality.text = "Kewarganegaraan : ${data.wali?.kewarganegaraan ?: "Tidak ada data"}"
            binding.tvGuardianEducation.text = "Riwayat Pendidikan : ${data.wali?.pendidikan ?: "Tidak ada data"}"
            binding.tvGuardianOccupation.text = "Pekerjaan : ${data.wali?.pekerjaan ?: "Tidak ada data"}"
            binding.tvGuardianExpenditure.text = "Pengeluaran per Bulan : ${data.wali?.pengeluaranPerBulan ?: "Tidak ada data"}"
            binding.tvGuardianAddressAndPhone.text = "Alamat dan Telp : ${data.wali?.alamatDanNoTelepon ?: "Tidak ada data"}"
//            binding.tvGuardianStatus.text = "Masih Hidup/Meninggal Dunia : ${data.wali?.status ?: "Tidak ada data"}"

            //Perkembangan Siswa
            binding.tvScholarshipReceived.text = "Menerima Beasiswa : ${data.perkembangan?.menerimaBeaSiswaTahunKelasDari ?: "Tidak ada data"}"
            binding.tvLeftSchool.text = "Meninggalkan Sekolah : ${data.perkembangan?.meninggalkanSekolahIniAlasan ?: "Tidak ada data"}"
            binding.tvEducationEnd.text = "Akhir Pendidikan : ${data.perkembangan?.akhirPendidikanTamatBelajarLulusTahun ?: "Tidak ada data"}"

            //Setelah Lulus
            binding.tvContinueTo.text = "Melanjutkan Ke : ${data.setelahPendidikan?.melanjutkanKe ?: "Tidak ada data"}"
            binding.tvWorkInCompany.text = "Bekerja Di Perusahaan : ${data.setelahPendidikan?.bekerjaNamaPerusahaan ?: "Tidak ada data"}"
            binding.tvStartWorkDate.text = "Tanggal Mulai Bekerja : ${data.setelahPendidikan?.bekerjaTanggalMulai ?: "Tidak ada data"}"
            binding.tvIncome.text = "Penghasilan : ${data.setelahPendidikan?.bekerjaPenghasilan ?: "Tidak ada data"}"

        })

        binding.informationButton.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, EditFormStudentFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

    }

    private fun showCustomSnackbar() {
        val customSnackbar = View.inflate(requireContext(), R.layout.custom_snackbar, null)
        val binding = CustomSnackbarBinding.bind(customSnackbar)
        val snackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG)

        snackbar.apply {
            (view as ViewGroup).addView(customSnackbar)

            val layoutParams = snackbar.view.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            snackbar.view.layoutParams = layoutParams

            show()
        }
    }
}