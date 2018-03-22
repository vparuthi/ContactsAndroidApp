package com.example.veraj.contacttrial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class Display extends AppCompatActivity {
    public static final String CLICKED_CONTACT = "Clicked Contact";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Veraj");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra(CLICKED_CONTACT);
        //getSupportActionBar().setTitle(contact.getFirstName());
        TextView firstName = (TextView) findViewById(R.id.firstName);
        firstName.setText(contact.getFirstName());
        ImageView contactPhoto = (ImageView) findViewById(R.id.contactPhoto);
        contactPhoto.setImageBitmap(getThumbnail("contact" + contact.getFirstName() +".png"));

    }
    public static Intent makeIntent (Context context){
        Intent intent = new Intent(context, Display.class);
        return intent;
    }

    public Bitmap getThumbnail(String filename) {
        Bitmap thumbnail = null;
        try {
            File filePath = getFileStreamPath(filename);
            FileInputStream fi = new FileInputStream(filePath);
            thumbnail = BitmapFactory.decodeStream(fi);
        } catch (Exception ex) {
            Log.e("Thumbnail on internal", ex.getMessage());
        }
        return thumbnail;
    }


}
