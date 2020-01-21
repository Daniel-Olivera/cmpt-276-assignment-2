package com.depthoffieldcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.depthoffieldcalculator.Model.*;

public class AddLens extends AppCompatActivity {

    String make;
    int focal;
    double aperture;

    private LensManager manager;


    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, AddLens.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = LensManager.getInstance();

        Button saveButton = findViewById(R.id.btnSave);
        EditText lensMake = findViewById(R.id.lensMake);
        EditText lensFocal = findViewById(R.id.lensFocal);
        EditText lensAp = findViewById(R.id.lensAperture);

        saveButton.setOnClickListener(view ->{
                make = lensMake.getText().toString();
                focal = Integer.valueOf(lensFocal.getText().toString());
                aperture = Double.valueOf(lensAp.getText().toString());

            Toast.makeText(AddLens.this,"Saved!",Toast.LENGTH_SHORT).show();
            addLens(make,focal,aperture);

         });
    }


    void addLens(String model, int focalLength, double ap){
        manager.add(new Lens(model,ap,focalLength));
    }


}
