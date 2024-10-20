package com.riyanto.belajarsqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.riyanto.belajarsqlite.helpers.DatabaseHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText etNim, etNama;
    Spinner spProdi;
    Button btnUpdate, btnDelete;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        spProdi = findViewById(R.id.sp_prodi);
        btnUpdate = findViewById(R.id.btn_ubah);
        btnDelete = findViewById(R.id.btn_delete);
        databaseHelper = new DatabaseHelper(this);

        // Mendapatkan data dari Intent
        String nim = getIntent().getStringExtra("nim");
        String nama = getIntent().getStringExtra("nama");
        String prodi = getIntent().getStringExtra("prodi");

        // Set data ke input fields
        etNim.setText(nim);
        etNama.setText(nama);
        String[] arrProdi = {"Manajemen Informatika", "Teknik Listrik"};
        spProdi.setAdapter(new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrProdi));
        spProdi.setSelection(getIndex(spProdi, prodi));

        btnUpdate.setOnClickListener(v -> updateMahasiswa(nim));
        btnDelete.setOnClickListener(v -> deleteMahasiswa(nim));
    }

    private void updateMahasiswa(String nim) {
        String nama = etNama.getText().toString();
        String prodi = spProdi.getSelectedItem().toString();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("prodi", prodi);

        db.update("mahasiswa", values, "nim = ?", new String[]{nim});
        finish();
    }

    private void deleteMahasiswa(String nim) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("mahasiswa", "nim = ?", new String[]{nim});
        finish();
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }
}
