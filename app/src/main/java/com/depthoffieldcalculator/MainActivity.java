package com.depthoffieldcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.depthoffieldcalculator.Model.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public LensManager manager;
    public static int pos; //used for tracking which item in the list is clicked


    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateListView();
        registerClickCallback();

        FloatingActionButton fab = findViewById(R.id.fab); //sends user to the AddLens screen
        fab.setOnClickListener(view -> {
                Intent i = AddLens.makeLaunchIntent(MainActivity.this);
                startActivity(i);
            });
    }

    private void populateListView() {

        manager = LensManager.getInstance();//pulls instance

        ArrayList<String> lensArray = new ArrayList<>();//setup an array to store the lenses in for the listView

        //Pre-populates the list with lenses
        if(manager.lenses.size() == 0){
            manager.add(new Lens("Canon",1.8,50));
            manager.add(new Lens("Tamron",2.8,90));
            manager.add(new Lens("Sigma",2.8,200));
            manager.add(new Lens("Nikon",4.0,200));
        }

      for (Lens lens: manager){
          lensArray.add(toString(lens));
      }

        ListView list = findViewById(R.id.lensList);
        //Adapter to show the lensArray in the list on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lensArray);
        list.setAdapter(adapter);
    }

    //depending on which lens is selected from the list,
    // this sends the user to the calculator screen
    private void registerClickCallback() {
        ListView list = findViewById(R.id.lensList);
        list.setOnItemClickListener((parent, viewClicked, position, id) -> {
            pos = position; //sends the position in the list for indexing in the calculator
            Intent i = Calculator.makeLaunchIntent(MainActivity.this);
            startActivity(i);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //converts the lens data into string for onscreen display
    public String toString(Lens lens){
        String lensMake = lens.make;
        String lensFocal = Integer.toString(lens.focalLength);
        String lensAp = Double.toString(lens.maxAperture);

        return lensMake + " " + lensFocal + "mm F" + lensAp;
    }


}
