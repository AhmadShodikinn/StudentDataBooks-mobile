package com.project.virtualdatabooks.Data.Response

import com.google.gson.annotations.SerializedName

data class AdminGetPendingRequestByIdResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class PendingIdJurusan(

	@field:SerializedName("nama")
	val nama: String? = null
)

data class Data(

	@field:SerializedName("ibu_kandung")
	val ibuKandung: PendingIdIbuKandung? = null,

	@field:SerializedName("data_diri")
	val dataDiri: PendingIdDataDiri? = null,

	@field:SerializedName("kesehatan")
	val kesehatan: PendingIdKesehatan? = null,

	@field:SerializedName("setelah_pendidikan")
	val setelahPendidikan: PendingIdSetelahPendidikan? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: PendingIdPendidikan? = null,

	@field:SerializedName("nisn")
	val nisn: String? = null,

	@field:SerializedName("angkatan")
	val angkatan: PendingIdAngkatan? = null,

	@field:SerializedName("jurusan")
	val jurusan: PendingIdJurusan? = null,

	@field:SerializedName("tempat_tinggal")
	val tempatTinggal: PendingIdTempatTinggal? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("wali")
	val wali: PendingIdWali? = null,

	@field:SerializedName("hobi_siswa")
	val hobiSiswa: PendingIdHobiSiswa? = null,

	@field:SerializedName("ayah_kandung")
	val ayahKandung: PendingIdAyahKandung? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("angkatan_id")
	val angkatanId: Int? = null,

	@field:SerializedName("jurusan_id")
	val jurusanId: Int? = null,

	@field:SerializedName("perkembangan")
	val perkembangan: PendingIdPerkembangan? = null
)

data class PendingIdTempatTinggal(

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("jarak_ke_sekolah")
	val jarakKeSekolah: String? = null,

	@field:SerializedName("tinggal_dengan")
	val tinggalDengan: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("no_telepon")
	val noTelepon: String? = null
)

data class PendingIdPerkembangan(

	@field:SerializedName("meninggalkan_sekolah_ini_alasan")
	val meninggalkanSekolahIniAlasan: String? = null,

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("menerima_bea_siswa_tahun_kelas_dari")
	val menerimaBeaSiswaTahunKelasDari: String? = null,

	@field:SerializedName("akhir_pendidikan_no_tanggal_skhun")
	val akhirPendidikanNoTanggalSkhun: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("akhir_pendidikan_no_tanggal_ijazah")
	val akhirPendidikanNoTanggalIjazah: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("meninggalkan_sekolah_ini_tanggal")
	val meninggalkanSekolahIniTanggal: String? = null,

	@field:SerializedName("akhir_pendidikan_tamat_belajar_lulus_tahun")
	val akhirPendidikanTamatBelajarLulusTahun: String? = null
)

data class PendingIdSetelahPendidikan(

	@field:SerializedName("bekerja_tanggal_mulai")
	val bekerjaTanggalMulai: String? = null,

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("melanjutkan_ke")
	val melanjutkanKe: String? = null,

	@field:SerializedName("bekerja_penghasilan")
	val bekerjaPenghasilan: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("bekerja_nama_perusahaan")
	val bekerjaNamaPerusahaan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class PendingIdAyahKandung(

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("pengeluaran_per_bulan")
	val pengeluaranPerBulan: String? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,

	@field:SerializedName("agama")
	val agama: String? = null,

	@field:SerializedName("kewarganegaraan")
	val kewarganegaraan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String? = null,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("alamat_dan_no_telepon")
	val alamatDanNoTelepon: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PendingIdDataDiri(

	@field:SerializedName("kelengkapan_ortu")
	val kelengkapanOrtu: String? = null,

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("agama")
	val agama: String? = null,

	@field:SerializedName("jml_saudara_kandung")
	val jmlSaudaraKandung: Int? = null,

	@field:SerializedName("kewarganegaraan")
	val kewarganegaraan: String? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String? = null,

	@field:SerializedName("jml_saudara_angkat")
	val jmlSaudaraAngkat: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("bahasa_sehari_hari")
	val bahasaSehariHari: String? = null,

	@field:SerializedName("anak_ke")
	val anakKe: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nama_panggilan")
	val namaPanggilan: String? = null,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("jml_saudara_tiri")
	val jmlSaudaraTiri: Int? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null
)

data class PendingIdAngkatan(

	@field:SerializedName("tahun")
	val tahun: Int? = null
)

data class PendingIdKesehatan(

	@field:SerializedName("penyakit_pernah_diderita")
	val penyakitPernahDiderita: String? = null,

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("kelainan_jasmani")
	val kelainanJasmani: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("gol_darah")
	val golDarah: String? = null,

	@field:SerializedName("berat_badan")
	val beratBadan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tinggi")
	val tinggi: String? = null
)

data class PendingIdWali(

	@field:SerializedName("kewarganegaraan")
	val kewarganegaraan: String? = null,

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("pengeluaran_per_bulan")
	val pengeluaranPerBulan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("alamat_dan_no_telepon")
	val alamatDanNoTelepon: String? = null,

	@field:SerializedName("agama")
	val agama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null
)

data class PendingIdHobiSiswa(

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("olahraga")
	val olahraga: String? = null,

	@field:SerializedName("organisasi")
	val organisasi: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("kesenian")
	val kesenian: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lain_lain")
	val lainLain: String? = null
)

data class PendingIdIbuKandung(

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("pengeluaran_per_bulan")
	val pengeluaranPerBulan: String? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,

	@field:SerializedName("agama")
	val agama: String? = null,

	@field:SerializedName("kewarganegaraan")
	val kewarganegaraan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String? = null,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("alamat_dan_no_telepon")
	val alamatDanNoTelepon: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PendingIdPendidikan(

	@field:SerializedName("sebelumnya_tanggal_skhun_dan_")
	val sebelumnyaTanggalSkhunDan: String? = null,

	@field:SerializedName("status_perubahan")
	val statusPerubahan: String? = null,

	@field:SerializedName("pindahan_dari_sekolah")
	val pindahanDariSekolah: String? = null,

	@field:SerializedName("diterima_tanggal")
	val diterimaTanggal: String? = null,

	@field:SerializedName("pindahan_alasan")
	val pindahanAlasan: String? = null,

	@field:SerializedName("diterima_di_paket_keahlian")
	val diterimaDiPaketKeahlian: String? = null,

	@field:SerializedName("sebelumnya_tamatan_dari")
	val sebelumnyaTamatanDari: String? = null,

	@field:SerializedName("sebelumnya_lama_belajar")
	val sebelumnyaLamaBelajar: String? = null,

	@field:SerializedName("diterima_di_kelas")
	val diterimaDiKelas: Int? = null,

	@field:SerializedName("diterima_di_bidang_keahlian")
	val diterimaDiBidangKeahlian: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("diterima_di_program_keahlian")
	val diterimaDiProgramKeahlian: String? = null,

	@field:SerializedName("sebelumnya_tanggal_dan_ijazah")
	val sebelumnyaTanggalDanIjazah: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
