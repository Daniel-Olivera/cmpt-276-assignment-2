package com.depthoffieldcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.depthoffieldcalculator.Model.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public LensManager manager;



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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
                Intent i = AddLens.makeLaunchIntent(MainActivity.this);
                startActivity(i);
            });
    }



    private void populateListView() {
        manager = LensManager.getInstance();
//        manager.add(new Lens("Canon",1.8,50));
//        manager.add(new Lens("Tamron",2.8,50));
//        manager.add(new Lens("Sigma",2.8,200));
//        manager.add(new Lens("Nikon",4,200));

        ArrayList<String> lensArray = new ArrayList<>();

      for (Lens lens: manager){
          lensArray.add(toString(lens));
      }
        ListView list = findViewById(R.id.lensList);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lensArray);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.lensList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView txt = (TextView) viewClicked;
                String message = "You clicked # " + position
                    + " which is string: " + txt.getText().toString();
                Toast.makeText(MainActivity.this, message,Toast.LENGTH_SHORT).show();
            }
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

    public String toString(Lens lens){
        String lensMake = lens.make;
        String lensFocal = Integer.toString(lens.focalLength);
        String lensAp = Double.toString(lens.maxAperture);

        return lensMake + " " + lensFocal + "mm F" + lensAp;
    }


}
