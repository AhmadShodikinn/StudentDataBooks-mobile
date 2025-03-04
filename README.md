# Virtual Data Book - Aplikasi Mobile

Aplikasi **Virtual Data Book** adalah aplikasi mobile yang digunakan untuk menyimpan dan mengelola data siswa secara virtual. Aplikasi ini dibangun dengan menggunakan **Kotlin** dan **Android Studio** serta menggunakan **Material UI** untuk tampilan dan Penggunaan retrofit untuk berkomunikasi dengan api server

## Fitur Utama
- Mengubah data siswa.
- Melihat data siswa berdasarkan angkatan,jurusan,tahun angkatan.
- Autentikasi menggunakan email, otp, jwt dari api.
- Tampilan antarmuka yang sederhana dan mudah digunakan.

## Minimum Requirements

Untuk menjalankan aplikasi **Virtual Data Book** pada perangkat atau emulator Android, pastikan sistem Anda memenuhi persyaratan minimum berikut:

### Sistem Operasi dan Perangkat
- **Android Version**: Android 9 (Pie) atau yang lebih baru.
- **Perangkat Fisik**: Perangkat Android dengan minimal Android 9 (Pie) atau lebih baru.
- **Emulator**: Emulator Android dengan Android API Level 28 atau lebih tinggi.

### Perangkat Lunak
- **Android Studio**: Versi terbaru (disarankan **Android Studio Electric Eel** atau lebih baru).
- **Android Gradle Plugin (AGP)**: **8.3.2** atau yang lebih baru.
- **Kotlin**: Versi terbaru yang didukung oleh AGP 8.3.2.
- **Gradle**: Versi terbaru yang kompatibel dengan Android Gradle Plugin 8.3.2.

### SDK dan Tools
- **SDK Android**: Pastikan Anda menginstal Android SDK dengan API Level 28 atau lebih tinggi.
- **Java**: JDK 11 atau lebih tinggi.
- **Node.js**: diperlukan untuk akses server

### Spesifikasi Minimum Perangkat
- **RAM**: 4 GB atau lebih (disarankan untuk emulator atau perangkat fisik).
- **Penyimpanan**: 1 GB ruang kosong atau lebih.


## Langkah-Langkah Instalasi

### 1. Clone Repository

Clone repositori ini ke dalam direktori lokal Anda menggunakan Git. Pastikan Anda telah menginstal [Git](https://git-scm.com/) pada sistem Anda.

```bash
git clone https://github.com/username/Virtual-Data-Book-mobile.git
```

### 2. Buka android studio IDE
- Buka Android Studio.
- Pilih Open an Existing Project.
- Arahkan ke folder hasil clone repositori dan pilih direktori proyek.


### 3. Sinkronisasi Proyek dengan Gradle
Setelah proyek terbuka di Android Studio, sistem akan otomatis mendeteksi file build.gradle dan menanyakan apakah Anda ingin menyinkronkan proyek dengan Gradle. Klik ``Sync Now`` untuk memulai proses tersebut.

note: Jika terjadi masalah dengan Gradle, pastikan Anda menggunakan versi Android Studio yang kompatibel dengan Gradle yang digunakan di proyek ini.

proyek ini membutuhkan server/backend untuk dapat digunakan, anda bisa mengarah ke [backend-server](https://github.com/AnandaCahya/backend-buku-induk)  untuk informasi lebih lanjut

setelah konfigurasi sistem backend dilakukan anda perlu melakukan konfigurasi apiconfig server pada android studio di 

``project >> virtualdatabooks >> network >> ApiConfig``

ubah konfigurasi localhost sesuatu dengan ip anda

### 4. Menambahkan Emulator atau Menyambungkan Perangkat
Anda dapat menjalankan aplikasi menggunakan Emulator Android atau perangkat Android fisik.

- Menggunakan Emulator:
Buka AVD Manager di Android Studio (Tools > AVD Manager).
Pilih atau buat emulator baru dengan spesifikasi yang sesuai (misalnya, dengan API yang kompatibel dengan aplikasi ini).
Klik Run untuk menjalankan aplikasi di emulator.

- Menggunakan Perangkat Fisik:
Aktifkan Developer Options dan USB Debugging di perangkat Android Anda.
Hubungkan perangkat ke komputer menggunakan kabel USB.
Pilih perangkat yang terhubung pada Android Studio, lalu klik Run.

### 5. Menjalankan Aplikasi
Setelah emulator atau perangkat fisik disiapkan, Anda dapat menjalankan aplikasi dengan mengklik tombol Run di Android Studio.

Tunggu hingga aplikasi berhasil dibangun dan diluncurkan di emulator/perangkat Anda.
