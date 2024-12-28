package com.example.suivipatient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Ajout extends AppCompatActivity {

    private EditText edNom;
    private EditText edPrenom;
    private EditText edDate;
    private Button btnAjouter;
    private Button btnRetour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        init();
    }
    private void init() {
        edNom = (EditText) findViewById(R.id.edNom);
        edPrenom = (EditText) findViewById(R.id.edPrenom);
        edDate = (EditText) findViewById(R.id.edDate);
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        btnRetour = (Button) findViewById(R.id.btnRetour);
        ecouteurs();
    }

    private void ecouteurs() {
        btnRetour.setOnClickListener(view ->finish());
        btnAjouter.setOnClickListener(view ->ajouter());
    }

    private void ajouter() {

        SQLiteSuivi b=new SQLiteSuivi(this,"suivi.db" ,null,1);
        SQLiteDatabase db = b.getReadableDatabase();
        ContentValues v = new ContentValues();
        v.put("nom",edNom.getText().toString());
        v.put("prenom",edPrenom.getText().toString());
        v.put("Date",edDate.getText().toString());
        db.insert("patient",null,v);
        db.close();
        setResult(RESULT_OK);
        finish();
    }


}
