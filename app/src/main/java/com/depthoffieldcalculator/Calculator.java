package com.depthoffieldcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.depthoffieldcalculator.Model.DoFCalculator;
import com.depthoffieldcalculator.Model.Lens;
import com.depthoffieldcalculator.Model.LensManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static com.depthoffieldcalculator.MainActivity.pos;

public class Calculator extends AppCompatActivity {

    private LensManager manager;

    private float coc;
    private float dist;
    private float aperture;

    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, Calculator.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //pull singleton
        manager = LensManager.getInstance();

        setupCalculator();

    }



    //sets up the calculator button
    private void setupCalculator() {
        //get the right lens depending on which one we click
        Lens lens = manager.get(pos);
        //setup the text editors and the button
        EditText editCoC = findViewById(R.id.editTextCoC);
        EditText editDist = findViewById(R.id.editTextDist);
        EditText editAp = findViewById(R.id.editTextAper);
        Button btnCalc = findViewById(R.id.btnCalc);


        //setup the button click listener
        btnCalc.setOnClickListener(view -> {
            //if the fields are empty, let the user know
            if (TextUtils.isEmpty(editCoC.getText().toString()) ||
                    TextUtils.isEmpty(editDist.getText().toString()) ||
                    TextUtils.isEmpty(editAp.getText().toString())) {
                Toast.makeText(Calculator.this, "Invalid input", Toast.LENGTH_LONG).show();
            }
            //if they are not empty apply the values
            else {
                coc = Float.valueOf(editCoC.getText().toString());
                dist = Float.valueOf(editDist.getText().toString());
                aperture = Float.valueOf(editAp.getText().toString());
                //distance to target cannot be 0 or less
                if(dist <= 0){
                    Toast.makeText(Calculator.this, "Distance must be greater than 0m", Toast.LENGTH_LONG).show();
                }
                //user cannot exceed the maximum aperture
                else if(aperture < lens.maxAperture-0.01){
                    String msg = "Please input an F value of at least " + lens.maxAperture;
                    Toast.makeText(Calculator.this, msg, Toast.LENGTH_LONG).show();
                }
                else{
                calculate(coc,dist,aperture);
                }
            }
        });
    }
    //does all the calculations using the DoFCalculator Class
    private void calculate(float coc, float dist, float aperture) {
        DoFCalculator dof = new DoFCalculator(manager);

        //get the numbers from the DoFCalculator
        double focalNear = dof.getDofNear(pos, aperture, dist, coc)/1000;
        double focalFar = dof.getDofFar(pos, aperture, dist, coc)/1000;
        double DoF = focalFar - focalNear;
        double hyper = dof.getHyperDist(pos, aperture, coc)/1000;

        display(focalNear,focalFar,DoF,hyper,dist);
    }

    private void display(double near, double far, double dof, double hyper, float dist){

        String nearMsg = formatM(near);
        String farMsg = formatM(far);
        String DoF = formatM(dof);
        String hyperMsg = formatM(hyper);

        //if the distance to target is greater than the hyperfocal distance, then the fov is infinite
        if(dist > hyper){
            farMsg = "\u221E" + "m";
            DoF = "\u221E" + "m";
        }
        //set up the calculator displays
        TextView nearFocal = findViewById(R.id.txtNearNum);
        TextView farFocal = findViewById(R.id.txtFarNum);
        TextView Depth = findViewById(R.id.txtDoFNum);
        TextView hyperFocal = findViewById(R.id.txtHyperNum);
        //show the calculated results on screen
        nearFocal.setText(nearMsg);
        farFocal.setText(farMsg);
        Depth.setText(DoF);
        hyperFocal.setText(hyperMsg);
    }

    //Formatting method taken from Assignment 1
    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00m");
        return df.format(distanceInM);
    }
}

