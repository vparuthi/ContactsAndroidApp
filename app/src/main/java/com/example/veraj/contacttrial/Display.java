package com.example.veraj.contacttrial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends AppCompatActivity {
    public static final String CLICKED_CONTACT = "Clicked Contact";
    public static final int EDIT_CONTACT = 001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Veraj");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddContact.makeIntent(Display.this);
                startActivityForResult(intent, EDIT_CONTACT);
            }
        });

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra(CLICKED_CONTACT);
        TextView firstName = (TextView) findViewById(R.id.firstName);
        firstName.setText(contact.getFirstName());
        ImageView contactPhoto = (ImageView) findViewById(R.id.contactPhoto);
        contactPhoto.setImageBitmap(BitmapUtil.getThumbnail("contact" + contact.getFirstName() +".png", this));
        getSupportActionBar().setTitle(contact.getFirstName() + " " + contact.getLastName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case EDIT_CONTACT:
                if (resultCode == RESULT_OK){
                    Toast.makeText(this, "Editted", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public static Intent makeIntent (Context context){
        Intent intent = new Intent(context, Display.class);
        return intent;
    }
}
