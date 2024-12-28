package com.example.suivipatient;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   
    private Spinner spPatient;
    private EditText edTension;
    private EditText edRythme;
    private Button btnModifier;
    private Button btnNouveau;
    private ArrayAdapter<Patient> adpPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        spPatient = (Spinner) findViewById(R.id.spPatient);
        edTension = (EditText) findViewById(R.id.edTension);
        edRythme = (EditText) findViewById(R.id.edRythme);
        btnModifier = (Button) findViewById(R.id.btnModifier);
        btnNouveau = (Button) findViewById(R.id.btnNouveau);
        adpPatient = new ArrayAdapter<Patient>(this,
                android.R.layout.simple_spinner_dropdown_item);
        spPatient.setAdapter(adpPatient);
        ecouteur();
        remplir();
       
    }

    private void remplir() {

            SQLiteSuivi dbHelper = new SQLiteSuivi(this, "suivi.db", null, 1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("patient", null, null, null, null, null, null);

            adpPatient.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String nom = cursor.getString(1);
                String prenom = cursor.getString(2);
                String date = cursor.getString(3);
                int tension = cursor.getInt(4);
                int rythme = cursor.getInt(5);
                adpPatient.add(new Patient(id, nom, prenom, date,tension,rythme));
            }
            cursor.close();
            db.close();

            if (adpPatient.isEmpty()) {
                Toast.makeText(this, "No patient available to update.", Toast.LENGTH_SHORT).show();
            }

    }

    ActivityResultLauncher<Intent> ActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    remplir();
                }
            });

    private void ecouteur() {
        btnNouveau.setOnClickListener(view -> ajout());
        spPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Patient selectedPatient = (Patient) spPatient.getSelectedItem();
                if (selectedPatient != null) {
                   edTension.setText(String.valueOf(selectedPatient.getTension()));
                    edRythme.setText(String.valueOf(selectedPatient.getRythme()));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edTension.setText("");
                edRythme.setText("");
            }
        });
        btnModifier.setOnClickListener(view -> modifier());
    }
    private void ajout() {
        Intent i=new Intent(this, Ajout.class);
        ActivityLauncher.launch(i);
    }

    private void modifier() {
        Patient selectedPatient = (Patient) spPatient.getSelectedItem();
        if (selectedPatient != null) {
            try {
                int newTension = Integer.parseInt(edTension.getText().toString());
                int newRythme = Integer.parseInt(edRythme.getText().toString());

                SQLiteSuivi dbHelper = new SQLiteSuivi(this, "suivi.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("tension", newTension);
                values.put("rythme", newRythme);

                int rowsAffected = db.update(
                        "patient",
                        values,
                        "id = ?",
                        new String[]{String.valueOf(selectedPatient.getId())}
                );

                db.close();

                if (rowsAffected > 0) {
                    Toast.makeText(this, "Patient updated successfully", Toast.LENGTH_SHORT).show();
                    remplir();
                } else {
                    Toast.makeText(this, "Failed to update patient", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers for tension and rythme", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No patient selected", Toast.LENGTH_SHORT).show();
        }
    }


}


