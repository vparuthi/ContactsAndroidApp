package com.example.veraj.contacttrial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "com.example.veraj.contacttrial";
    public static final int REQUEST_CODE = 001;
    public static final String CLICKED_CONTACT = "Clicked Contact";

    //List of Array String that will serve as the list items
    ArrayList<Contact> listItems = new ArrayList<Contact>();

    //Defining a string Adapter that will handle the ListView Data
    ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView list = (ListView) findViewById(R.id.listView);

        adapter = new ContactAdapter(this, listItems);
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
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("myImage"));
                        //contact.setContactPhoto(bitmap);
                        addContact(contact);
                    } catch (FileNotFoundException e) {
                    }

                    Log.i(TAG, contact.getFirstName());
                    //addContact(contact);
                }
                else {
                    Log.i(TAG, "AddContact Activity Cancelled");
                }
                break;

        }
    }

    public void addContact(Contact contact) {
        listItems.add(contact);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView noContactsPresent = (TextView) findViewById(R.id.noContactsText);
        if (listItems.size() == 0) {
            noContactsPresent.setVisibility(View.VISIBLE);
        }
        else {
            noContactsPresent.setVisibility(View.GONE);
        }
        adapter.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getFirstName().compareTo(c2.getFirstName());
            }
        });

        final ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = Display.makeIntent(MainActivity.this);
                intent.putExtra(CLICKED_CONTACT, listItems.get(i));
                startActivity(intent);
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
}
