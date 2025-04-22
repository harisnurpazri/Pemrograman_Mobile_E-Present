package com.example.e_present

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etNim: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etDate: TextInputEditText
    private lateinit var etTime: TextInputEditText
    private lateinit var btnSubmit: MaterialButton

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi views
        etName = findViewById(R.id.etName)
        etNim = findViewById(R.id.etNim)
        etPhone = findViewById(R.id.etPhone)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Atur tanggal dan waktu default
        updateDateInView()
        updateTimeInView()

        // Atur listener untuk pemilih tanggal dan waktu
        etDate.setOnClickListener {
            showDatePicker()
        }

        etTime.setOnClickListener {
            showTimePicker()
        }

        // Atur listener untuk tombol kirim
        btnSubmit.setOnClickListener {
            if (validateInputs()) {
                submitAttendance()
            } else {
                Toast.makeText(this, getString(R.string.field_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Menampilkan dialog pemilihan tanggal
    private fun showDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        DatePickerDialog(
            this, dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Menampilkan dialog pemilihan waktu
    private fun showTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            updateTimeInView()
        }

        TimePickerDialog(
            this, timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    // Memperbarui tampilan tanggal
    private fun updateDateInView() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        etDate.setText(sdf.format(calendar.time))
    }

    // Memperbarui tampilan waktu
    private fun updateTimeInView() {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        etTime.setText(sdf.format(calendar.time))
    }

    // Validasi semua input form
    private fun validateInputs(): Boolean {
        val name = etName.text.toString().trim()
        val nim = etNim.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val date = etDate.text.toString().trim()
        val time = etTime.text.toString().trim()

        return name.isNotEmpty() && nim.isNotEmpty() && phone.isNotEmpty()
                && date.isNotEmpty() && time.isNotEmpty()
    }

    // Mengirim data absensi
    private fun submitAttendance() {
        val name = etName.text.toString().trim()
        val nim = etNim.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val date = etDate.text.toString().trim()
        val time = etTime.text.toString().trim()
        val dateTime = "$date $time"

        // Tampilkan dialog konfirmasi
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Absensi")
            .setMessage("""
                Nama  : $name
                NIM   : $nim
                No. HP: $phone
                Waktu : $dateTime
            """.trimIndent())
            .setPositiveButton("Konfirmasi") { _, _ ->
                // Tampilkan pesan sukses
                Toast.makeText(this, getString(R.string.attendance_success), Toast.LENGTH_LONG).show()

                // Reset formulir
                etName.text?.clear()
                etNim.text?.clear()
                etPhone.text?.clear()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}