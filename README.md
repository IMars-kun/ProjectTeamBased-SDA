## 🏥 Daisuke Clinic - Sistem Manajemen Rumah Sakit

> _Setiap hari, klinik menangani puluhan bahkan ratusan pasien. Di balik alur layanan yang terlihat sederhana, terdapat sistem yang kompleks untuk mencatat data pasien, menjadwalkan janji temu, dan mengelola informasi dokter._  
> _Proyek ini hadir untuk menyimulasikan sistem tersebut dengan pendekatan yang terstruktur._

**Daisuke Clinic** merupakan aplikasi berbasis Java yang dibangun untuk merepresentasikan sistem manajemen data klinik secara digital.  
Melalui proyek ini, konsep-konsep seperti Linked List, Queue, dan Binary Search Tree, diaplikasikan untuk mengatur data secara efisien dan terorganisir.

---

## 🗂️ Struktur Folder

```
├── src/
│ ├── Appointment.java
│ ├── BST.java
│ ├── Doctor.java
│ ├── FileHandler.java
│ ├── LinkedListDoctor.java
│ ├── LinkedListPatient.java
│ ├── Main.java
│ ├── Map.java
│ ├── MedicalRecord.java
│ ├── MenuFunction.java
│ ├── Node.java
│ └── Patient.java
├── ListDoctor.txt
├── ListPatient.txt
├── MedicalRecord.txt
├── UpComingAppointments.txt
└── README.md
```

---

## 🔍 Fitur Utama

Aplikasi ini dirancang untuk mensimulasikan sistem manajemen klinik secara digital dengan fitur-fitur berikut:

### 1. 👨‍⚕️ Manajemen Data Dokter dan Pasien

- Menambahkan dokter dan pasien baru.
- Menampilkan daftar lengkap dokter dan pasien.
- Pencarian berdasarkan ID atau nama menggunakan **Linked List** dan **Binary Search Tree (BST)**.

### 2. 📅 Sistem Janji Temu (Appointment)

- Menjadwalkan janji temu antara pasien dan dokter.
- Mengelola antrian janji temu dengan struktur data **Queue**.
- Penyimpanan data janji temu di `UpComingAppointments.txt`.

### 3. 🧾 Rekam Medis Pasien

- Input data rekam medis pasien setelah konsultasi.
- Informasi rekam medis mencakup ID pasien, ID dokter, diagnosa, pengobatan, resep, dan tanggal.
- Disimpan secara permanen di file `MedicalRecord.txt`.

### 4. 🔍 Pencarian Data

- **Dokter:** Menggunakan BST untuk pencarian cepat berdasarkan ID/nama.
- **Pasien:** Menggunakan Linked List untuk pencarian data pasien.

### 5. 💾 Penyimpanan Data

- Seluruh data disimpan dalam file `.txt` agar tetap tersimpan setelah program ditutup.
- Data otomatis dimuat ulang saat program dijalankan kembali.

---

## ⚙️ Fitur Program (Menu Interaktif)

Program menyediakan menu interaktif yang memudahkan pengguna untuk mengakses fitur-fitur berikut:

1. **Registrasi Pasien Baru**  
   ➤ Input data pasien baru dan simpan ke file.

2. **Registrasi Dokter Baru**  
   ➤ Tambahkan data dokter dan simpan ke dalam sistem.

3. **Lihat Daftar Pasien**  
   ➤ Tampilkan seluruh pasien yang telah terdaftar.

4. **Lihat Daftar Dokter**  
   ➤ Menampilkan daftar semua dokter yang tersedia.

5. **Jadwalkan Janji Temu**  
   ➤ Menambahkan pasien ke dalam sistem antrian janji.

6. **Layani Janji Temu**  
   ➤ Mengeluarkan pasien dari antrian dan input data rekam medis.

7. **Cari Dokter**  
   ➤ Pencarian dokter menggunakan BST berdasarkan ID atau nama.

8. **Cari Pasien**  
   ➤ Pencarian pasien menggunakan Linked List berdasarkan ID atau nama.

9. **Lihat Rekam Medis**  
   ➤ Menampilkan data rekam medis seluruh pasien.

10. **Keluar Program**  
    ➤ Menyimpan semua data dan keluar dari sistem.

## 🧠 Implementasi

### 1. 🧩 Struktur Node Pasien (LinkedList)

```java
class PatientNode {
    String id;
    String name;
    PatientNode next;

    public PatientNode(String id, String name) {
        this.id = id;
        this.name = name;
        this.next = null;
    }
}
```

> Konstruktor pada kelas `PatientNode` digunakan untuk menginisialisasi simpul (node) pasien baru. Nilai `next` diatur ke `null`, yang menunjukkan akhir dari rantai linked list. Struktur ini digunakan untuk menyimpan daftar pasien dalam bentuk berantai agar memudahkan traversal dan pencarian data pasien secara sekuensial.

---

### 2. 🏥 Penambahan Janji Temu Pasien (Queue)

```java
Queue<Appointment> appointmentQueue = new LinkedList<>();
Appointment ap = new Appointment("PSN001", "DKT001", "2025-06-17");
appointmentQueue.add(ap);
```

> Antrian janji temu menggunakan struktur `Queue` untuk memastikan urutan pelayanan pasien sesuai dengan waktu pendaftaran. Ini memungkinkan sistem memanggil pasien satu per satu sesuai urutan.

---

### 3. 🌳 Struktur Pencarian Dokter (Binary Search Tree)

```java
class TreeNode {
    Doctor data;
    TreeNode left, right;

    public TreeNode(Doctor data) {
        this.data = data;
        left = right = null;
    }
}
```

> Struktur BST ini memungkinkan pencarian data dokter berdasarkan ID dilakukan secara efisien. Pencarian dioptimalkan dengan membandingkan ID dan melanjutkan ke subtree kiri atau kanan.

---

### 4. 🧾 Menambahkan Rekam Medis Pasien (Map Sederhana)

```java
Map<String, MedicalRecord> records = new HashMap<>();
records.put("PSN001", new MedicalRecord("PSN001", "DTK001", "Demam", "Rawat Jalan", "Paracetamol", "2025-06-17"));
```

> Data rekam medis pasien disimpan menggunakan struktur map agar dapat diakses cepat berdasarkan ID pasien.

---

### 5. 📄 Menyimpan dan Membaca dari File

```java
FileWriter fw = new FileWriter("ListPatient.txt", true);
fw.write(p.getId() + "," + p.getName() + "\n");
fw.close();
```

> Data pasien disimpan dalam file teks untuk keperluan persistensi. File akan diperbarui setiap kali ada perubahan atau penambahan data baru.

---

## ▶️ Cara Menjalankan Program

Berikut langkah-langkah untuk menjalankan program ini di komputer Anda:

### 💼 Prasyarat

- Java Development Kit (JDK) versi 8 atau lebih tinggi
- Terminal / Command Prompt
- Text editor (opsional, seperti VSCode)

### 🛠️ Langkah-Langkah Menjalankan

**1. Buka Terminal dan Arahkan ke Folder `src/`**

```bash
cd src
```

**2. Kompilasi Semua File Java**

```bash
javac *.java
```

**3. Jalankan Program**

```bash
java Main.java
```

---

## 🖼️ Tampilan Program Saat Berjalan

Berikut adalah contoh tampilan program saat dijalankan melalui terminal:

### 🧑‍💼Login Sebagai Pasien

### 👩‍⚕Login Sebagai Dokter
![Tampilan Dokter]("Image/Doctor Menu.png")

---

## 👨‍💻 Kontribusi

1. <a href="https://github.com/aprliwln" target="_blank">Aprillia Wulandari</a> (L0124040) - BST, QUEUE, MAP
2. <a href="https://github.com/IMarsKun" target="_blank">Berly Marcellino Suprapto</a> (L0124045) - LINKEDLIST
3. <a href="https://github.com/ShaqiFiarahman" target="_blank">Fadel Shaqi Fiarahman</a> (L0124050) - STACK
4. <a href="https://github.com/DzakiRizal" target="_blank">Dzaki Rizal kurniawan</a> (L0124096) - DEBUGGER

---

<p align="center">
  Kelompok 4, Kelas C<br>
  Program Studi S-1 Informatika<br>
  Fakultas Teknologi Informasi dan Sains Data<br>
  Universitas Sebelas Maret<br>
  Tahun Ajaran 2024/2025
</p>
