package com.example.intent_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgFoto;
    Button btnPilihFoto, btnSubmit;
    EditText edtNama, edtNrp, edtKtp, edtUmur;
    RadioGroup rgGender;

    // Menyimpan Uri foto yang dipilih dari galeri
    private Uri fotoUri = null;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgFoto = findViewById(R.id.imgFoto);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        edtNama = findViewById(R.id.edtNama);
        edtNrp = findViewById(R.id.edtNrp);
        edtKtp = findViewById(R.id.edtKtp);
        edtUmur = findViewById(R.id.edtUmur);
        rgGender = findViewById(R.id.rgGender);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Registrasi Activity Result untuk memilih gambar dari galeri HP
        galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    fotoUri = uri;
                    imgFoto.setImageURI(uri);
                }
            }
        );

        // Tombol untuk membuka galeri foto
        btnPilihFoto.setOnClickListener(v -> {
            galleryLauncher.launch("image/*");
        });

        // Tombol Submit untuk mengirim data via Explicit Intent
        btnSubmit.setOnClickListener(v -> {

            String nama = edtNama.getText().toString().trim();
            String nrp = edtNrp.getText().toString().trim();
            String ktp = edtKtp.getText().toString().trim();
            String umur = edtUmur.getText().toString().trim();

            // Validasi Nama tidak boleh kosong
            if (nama.isEmpty()) {
                edtNama.setError("Nama tidak boleh kosong!");
                edtNama.requestFocus();
                return;
            }

            // Validasi NRP harus tepat 10 digit angka
            if (nrp.length() != 10) {
                edtNrp.setError("NRP harus terdiri dari 10 digit angka!");
                edtNrp.requestFocus();
                return;
            }

            // Validasi NIK KTP harus tepat 16 digit angka
            if (ktp.length() != 16) {
                edtKtp.setError("NIK KTP harus terdiri dari 16 digit angka!");
                edtKtp.requestFocus();
                return;
            }

            // Validasi Umur tidak boleh kosong
            if (umur.isEmpty()) {
                edtUmur.setError("Umur tidak boleh kosong!");
                edtUmur.requestFocus();
                return;
            }

            // Validasi Gender dari RadioGroup
            int selectedGenderId = rgGender.getCheckedRadioButtonId();
            if (selectedGenderId == -1) {
                Toast.makeText(MainActivity.this, "Silakan pilih jenis kelamin!", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedRadio = findViewById(selectedGenderId);
            String gender = selectedRadio.getText().toString();

            // Explicit Intent ke SecondActivity
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);

            // Kirim Uri Foto jika ada
            if (fotoUri != null) {
                intent.setData(fotoUri);
                intent.putExtra("fotoUri", fotoUri.toString());
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            intent.putExtra("nama", nama);
            intent.putExtra("nrp", nrp);
            intent.putExtra("ktp", ktp);
            intent.putExtra("umur", umur);
            intent.putExtra("gender", gender);

            startActivity(intent);

            // Bersihkan form input setelah kirim agar data tidak tersimpan di halaman 1
            clearForm();
        });
    }

    // Dipanggil saat kembali ke MainActivity setelah SecondActivity ditutup
    @Override
    protected void onRestart() {
        super.onRestart();
        clearForm();
    }

    // Method untuk mereset seluruh inputan formulir
    private void clearForm() {
        if (edtNama != null) edtNama.setText("");
        if (edtNrp != null) edtNrp.setText("");
        if (edtKtp != null) edtKtp.setText("");
        if (edtUmur != null) edtUmur.setText("");
        if (rgGender != null) rgGender.clearCheck();

        // Reset foto ke gambar default placeholder
        fotoUri = null;
        if (imgFoto != null) {
            imgFoto.setImageResource(R.drawable.ic_avatar_placeholder);
        }

        if (edtNama != null) {
            edtNama.setError(null);
            edtNama.requestFocus();
        }
        if (edtNrp != null) edtNrp.setError(null);
        if (edtKtp != null) edtKtp.setError(null);
        if (edtUmur != null) edtUmur.setError(null);
    }
}