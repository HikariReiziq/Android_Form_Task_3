package com.example.intent_activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    ImageView imgFotoSecond;
    TextView txtNama, txtNrp, txtKtp, txtUmur, txtGender;
    Button btnTutup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imgFotoSecond = findViewById(R.id.imgFotoSecond);
        txtNama = findViewById(R.id.txtNama);
        txtNrp = findViewById(R.id.txtNrp);
        txtKtp = findViewById(R.id.txtKtp);
        txtUmur = findViewById(R.id.txtUmur);
        txtGender = findViewById(R.id.txtGender);
        btnTutup = findViewById(R.id.btnTutup);

        // Mengambil foto profil yang dikirim melalui Intent
        Uri fotoUri = getIntent().getData();
        if (fotoUri == null && getIntent().hasExtra("fotoUri")) {
            fotoUri = Uri.parse(getIntent().getStringExtra("fotoUri"));
        }
        if (fotoUri != null) {
            imgFotoSecond.setImageURI(fotoUri);
        }

        // Mengambil biodata teks dari Intent
        String nama = getIntent().getStringExtra("nama");
        String nrp = getIntent().getStringExtra("nrp");
        String ktp = getIntent().getStringExtra("ktp");
        String umur = getIntent().getStringExtra("umur");
        String gender = getIntent().getStringExtra("gender");

        txtNama.setText("Nama: " + (nama != null ? nama : "-"));
        txtNrp.setText("NRP: " + (nrp != null ? nrp : "-"));
        txtKtp.setText("NIK KTP: " + (ktp != null ? ktp : "-"));
        txtUmur.setText("Umur: " + (umur != null ? umur : "-") + " Tahun");
        txtGender.setText("Jenis Kelamin: " + (gender != null ? gender : "-"));

        // Button Tutup menggunakan finish()
        btnTutup.setOnClickListener(v -> {
            finish();
        });
    }

    // Lifecycle onDestroy() dijalankan saat Activity ditutup / dihancurkan
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "SecondActivity onDestroy() dipanggil", Toast.LENGTH_SHORT).show();
    }
}
