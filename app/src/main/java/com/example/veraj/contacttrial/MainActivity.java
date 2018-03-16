package com.example.veraj.contacttrial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "com.example.veraj.contacttrial";
    public static final int REQUEST_CODE = 001;

    //List of Array String that will serve as the list items
    ArrayList<String> listItems = new ArrayList<String>();

    //Defining a string Adapter that will handle the ListView Data
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView list = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems);
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.addition_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Add Contact Button Pressed");
                Intent intent = AddContact.makeIntent(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE);
                //listItems.add("Clicked : "+clickCounter++);
                //adapter.notifyDataSetChanged();

            }
        });
    }

    //Listener for when a result is received back from AddContact
    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK){
                    Log.i(TAG, "Contact Received");
                    //Contact contact = (ContaTestct)data.getSerializableExtra("Contact Class");
                    Contact contact = (Contact) AddContact.getObject(data);
                    Log.i(TAG, contact.getFirstName());
                    addContact(contact);
                }
                else {
                    Log.i(TAG, "AddContact Activity Cancelled");
                }
                break;
            case 002:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        ImageButton addPhoto = (ImageButton) findViewById(R.id.addPhoto);
                        addPhoto.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    public void addContact(Contact contact) {
        listItems.add(contact.getFirstName() + " " + contact.getLastName());
        adapter.notifyDataSetChanged();
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
}
