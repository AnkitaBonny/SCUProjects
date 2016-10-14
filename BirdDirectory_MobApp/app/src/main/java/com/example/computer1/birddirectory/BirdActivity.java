package com.example.computer1.birddirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BirdActivity extends AppCompatActivity implements MyDialog.NoticeDialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ListView listView = (ListView) findViewById(R.id.custom_list_view);
        List<Animal> animal = new ArrayList<>();
        animal.add(new Animal("Macaw", "bird1t.jpg"));
        animal.add(new Animal("Parrot", "bird2t.jpg"));
        animal.add(new Animal("Lory", "bird3t.jpg"));
        animal.add(new Animal("Cockatoo", "bird4t.jpg"));
        animal.add(new Animal("Vulture", "bird5t.jpg"));
        listView.setAdapter(new CustomAdapter(this, R.layout.custom_row, animal));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bird, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_information) {
            Intent myIntent = new Intent(getApplicationContext(),InformationActivity.class);
            startActivity(myIntent);
            return true;
        }

        if (id == R.id.action_uninstall) {
            Uri packageURI = Uri.parse("package:com.example.computer1.birddirectory");
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //Toast.makeText(BirdActivity.this, "Yes clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(BirdActivity.this, DetailActivity.class);
        intent.putExtra("nameKey", "Vulture");
        startActivity(intent);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //Toast.makeText(BirdActivity.this, "No clicked", Toast.LENGTH_SHORT).show();;
        
    }


}



