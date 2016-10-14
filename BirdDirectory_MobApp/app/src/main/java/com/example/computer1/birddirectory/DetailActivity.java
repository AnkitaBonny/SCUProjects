package com.example.computer1.birddirectory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        String birdName = extras.getString("nameKey");

        TextView name = (TextView)findViewById(R.id.birdName);
        ImageView img = (ImageView)findViewById(R.id.birdImage);
        TextView desc = (TextView)findViewById(R.id.birdDescription);


        if ( birdName.equals("Macaw")) {
            name.setText("Bird Name : "  + birdName);
            try
            {
                // get input stream
                InputStream ims = getAssets().open("bird1.jpg");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                img.setImageDrawable(d);
            }
            catch(IOException ex)
            {
                return;
            }

            desc.setText("Macaws are long-tailed, beautiful, brilliantly colored members of the parrot family. ");


            return;

        }


        else if ( birdName.equals("Parrot")) {
            name.setText("Bird Name : "  + birdName);
            try
            {
                // get input stream
                InputStream ims = getAssets().open("bird2.jpg");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                img.setImageDrawable(d);
            }
            catch(IOException ex)
            {
                return;
            }

            desc.setText("Most intelligent birds, can imitate human voices");


            return;




        }

        else if ( birdName.equals("Lory")) {
            name.setText("Bird Name : "  + birdName);
            try
            {
                // get input stream
                InputStream ims = getAssets().open("bird3.jpg");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                img.setImageDrawable(d);
            }
            catch(IOException ex)
            {
                return;
            }

            desc.setText("Lory has specialized brush-tipped tongues for feeding on nectar and soft fruits ");


            return;




        }

        else if ( birdName.equals("Cockatoo")) {
            name.setText("Bird Name : "  + birdName);
            try
            {
                // get input stream
                InputStream ims = getAssets().open("bird4.jpg");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                img.setImageDrawable(d);
            }
            catch(IOException ex)
            {
                return;
            }

            desc.setText("Known as the umbrella cockatoo, When surprised, it extends a large and striking head crest ");


            return;




        }

        else if ( birdName.equals("Vulture")) {
            name.setText("Bird Name : "  + birdName);
            try
            {
                // get input stream
                InputStream ims = getAssets().open("bird5.jpg");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                img.setImageDrawable(d);
            }
            catch(IOException ex)
            {
                return;
            }

            desc.setText("Birds of prey, also known as raptors, hunt and feed on other animals, is a bald head, devoid of normal feathers");


            return;




        }


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

}
