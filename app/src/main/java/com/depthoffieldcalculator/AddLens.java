package com.depthoffieldcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
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

        //Creates buttons
        Button cancel = findViewById(R.id.btnCancel);
        Button saveButton = findViewById(R.id.btnSave);

        //Sets up variables for taking in user input
        EditText lensMake = findViewById(R.id.lensMake);
        EditText lensFocal = findViewById(R.id.lensFocal);
        EditText lensAp = findViewById(R.id.lensAperture);

        saveButton.setOnClickListener(view ->{
           //Checks if fields are empty
            if(TextUtils.isEmpty(lensMake.getText().toString()) ||
                    TextUtils.isEmpty(lensFocal.getText().toString()) ||
                    TextUtils.isEmpty(lensAp.getText().toString()))
            {
                Toast.makeText(AddLens.this, "Invalid input", Toast.LENGTH_LONG).show();
            }
            else{
                //takes user input
                make = lensMake.getText().toString();
                focal = Integer.valueOf(lensFocal.getText().toString());
                aperture = Double.valueOf(lensAp.getText().toString());

                //checks if the focal length or aperture are less than or equal to 0
                if(make == null || focal <= 0 || aperture <= 0){
                    Toast.makeText(AddLens.this, "Invalid input", Toast.LENGTH_LONG).show();
                }
                else{
                    //if all the input checks pass, input is actually saved
                Toast.makeText(AddLens.this, "Saved!", Toast.LENGTH_SHORT).show();
                addLens(make, focal, aperture); //Takes user input and adds it to the list
                //go back to Main
                Intent i = MainActivity.makeLaunchIntent(AddLens.this);
                startActivity(i);
                }
            }
         });

        //Cancel button takes you back to Main without adding a lens
        cancel.setOnClickListener(view ->{
            Toast.makeText(AddLens.this,"Canceled",Toast.LENGTH_SHORT).show();

            Intent i = MainActivity.makeLaunchIntent(AddLens.this);
            startActivity(i);
        });
    }


    void addLens(String model, int focalLength, double ap){
        manager.add(new Lens(model, ap, focalLength));
    }


}
