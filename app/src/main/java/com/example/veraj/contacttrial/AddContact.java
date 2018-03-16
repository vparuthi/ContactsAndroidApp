package com.example.veraj.contacttrial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

public class AddContact extends AppCompatActivity {

    public static final String CONTACT_CLASS = "Contact Class";
    public static final String TAG = "com.example.veraj.contacttrial";
    public static final int REQUEST_CODE_ADD_PHOTO = 002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        endActivityButton();

        ImageButton addPhoto = (ImageButton) findViewById(R.id.addPhoto);
        addPhoto.setImageResource(R.drawable.camera_icon);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Add Contact Photo");
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE_ADD_PHOTO);
                //photoPickerIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                //startActivity(photoPickerIntent);
            }
        });
    }

    public void endActivityButton (){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addContact);
        fab.setImageResource(R.drawable.save_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Save contact");
                EditText firstName = (EditText) findViewById(R.id.firstName);
                EditText lastName = (EditText) findViewById(R.id.lastName);
                final Contact contact = new Contact();

                //Extract Data from Add Contact UI
                contact.setFirstName(firstName.getText().toString());
                contact.setLastName(lastName.getText().toString());

                //Pass Data back
                Intent intent = new Intent();
                intent.putExtra(CONTACT_CLASS, contact);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }

    public static Intent makeIntent (Context context){
        Intent intent = new Intent(context, AddContact.class);
        return intent;
    }
    public static Serializable getObject (Intent intent){
        return intent.getSerializableExtra(CONTACT_CLASS);
    }


}
