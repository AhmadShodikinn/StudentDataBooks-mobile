package com.project.virtualdatabooks.Data.Request

data class StudentUpdateRequest(
    val data_diri: DataDiri,
    val perkembangan: DataPerkembangan,
    val ayah_kandung: DataAyah,
    val ibu_kandung: DataIbu,
    val kesehatan: DataKesehatan,
    val pendidikan: DataPendidikan,
    val setelah_pendidikan: DataSetelahPendidikan,
    val tempat_tinggal: DataTempatTinggal,
    val wali: DataWali,
    val hobi_siswa: DataHobi
)
data class StudentUpdateDataDiriRequest(
    val data_diri: DataDiri
)

data class DataDiri(
    val nama_lengkap: String,
    val nama_panggilan: String,
    val jenis_kelamin: String,
    val tempat_lahir: String,
    val agama: String,
    val kewarganegaraan: String,
    val anak_ke: Int,
    val jml_saudara_kandung: Int,
    val jml_saudara_tiri: Int,
    val jml_saudara_angkat: Int,
    val kelengkapan_ortu: String,
    val bahasa_sehari_hari: String,
    val tanggal_lahir: String?
)


data class StudentUpdateAyahRequest(
    val ayah_kandung: DataAyah
)

data class DataAyah (
    val nama: String,
    val tempat_lahir: String,
    val tanggal_lahir: String?,
    val agama: String,
    val kewarganegaraan: String,
    val pendidikan: String,
    val pekerjaan: String,
    val pengeluaran_per_bulan: String,
    val alamat_dan_no_telepon: String,
    val status: String
)

data class StudentUpdateIbuRequest(
    val ibu_kandung: DataIbu
)

data class DataIbu(
    val nama: String?,
    val tempat_lahir: String?,
    val tanggal_lahir: String?,
    val agama: String?,
    val kewarganegaraan: String?,
    val pendidikan: String?,
    val pekerjaan: String?,
    val pengeluaran_per_bulan: String?,
    val alamat_dan_no_telepon: String?,
    val status: String?
)

data class StudentUpdateHobiRequest(
    val hobi_siswa: DataHobi
)

data class DataHobi(
    val kesenian: String?,
    val olahraga: String?,
    val organisasi: String?,
    val lain_lain: String?
)

data class StudentUpdateKesehatanRequest(
    val kesehatan: DataKesehatan
)

data class DataKesehatan(
    val gol_darah: String,
    val penyakit_pernah_diderita: String,
    val kelainan_jasmani: String,
    val tinggi: String,
    val berat_badan: String
)

data class StudentUpdatePendidikanRequest(
    val pendidikan: DataPendidikan
)

data class DataPendidikan(
    val sebelumnya_tamatan_dari: String,
    val sebelumnya_tanggal_dan_ijazah: String,
    val sebelumnya_tanggal_skhun_dan_: String,
    val sebelumnya_lama_belajar: String,
    val pindahan_dari_sekolah: String?,
    val pindahan_alasan: String?,
    val diterima_di_kelas: Int,
    val diterima_di_bidang_keahlian: String,
    val diterima_di_program_keahlian: String,
    val diterima_di_paket_keahlian: String,
    val diterima_tanggal: String?
)

data class StudentUpdatePerkembanganRequest(
    val perkembangan: DataPerkembangan
)

data class DataPerkembangan(
    val menerimabea_siswa_tahun_kelas_dari: String,
    val meninggalkan_sekolah_ini_tanggal: String?,
    val meninggalkan_sekolah_ini_alasan: String,
    val akhir_pendidikan_tamat_belajar_lulus_tahun: String,
    val akhir_pendidikan_no_tanggal_ijazah: String,
    val akhir_pendidikan_no_tanggal_skhun: String
)

data class StudentUpdateSetelahPendidikanRequest(
    val setelah_pendidikan: DataSetelahPendidikan
)

data class DataSetelahPendidikan(
    val melanjutkan_ke: String?,
    val bekerja_nama_perusahaan: String?,
    val bekerja_tanggal_mulai: String?,
    val bekerja_penghasilan: String?
)

data class StudentUpdateTempatTinggalRequest(
    val tempat_tinggal: DataTempatTinggal
)

data class DataTempatTinggal(
    val alamat: String,
    val no_telepon: String,
    val tinggal_dengan: String,
    val jarak_ke_sekolah: String
)

data class StudentUpdateWaliRequest(
    val wali: DataWali
)

data class DataWali(
    val nama: String?,
    val tempat_lahir: String?,
    val tanggal_lahir: String?,
    val agama: String?,
    val kewarganegaraan: String?,
    val pendidikan: String?,
    val pekerjaan: String?,
    val pengeluaran_per_bulan: String?,
    val alamat_dan_no_telepon: String?
)


