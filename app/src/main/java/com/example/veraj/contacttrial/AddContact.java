package com.example.veraj.contacttrial;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

public class AddContact extends AppCompatActivity {

    public static final String CONTACT_CLASS = "Contact Class";
    public static final String TAG = "com.example.veraj.contacttrial";
    public static final int REQUEST_READ_PHOTO = 010;
    public static final int REQUEST_CODE_ADD_PHOTO = 001;
    private boolean checkIfAttahced = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        endActivityButton();
        ImageView addPhoto = (ImageView) findViewById(R.id.addPhoto);
        addPhoto.setImageResource(R.drawable.camera_icon);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Add Photo");
                if (Build.VERSION.SDK_INT > 22){
                    showGallery(view);
                } else{
                    openGallery();
                }
                checkIfAttahced = true;
            }
        });
    }

    public boolean saveImageToInternalStorage(Bitmap image, Contact contact) {

        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = this.openFileOutput("contact" + contact.getFirstName() + ".png" , Context.MODE_PRIVATE);
            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    private void showGallery(View view) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //if permission is already given
            openGallery();
        }else{
            //Permission not granted
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this, "Image Gallery permission is needed to set profile photo", Toast.LENGTH_SHORT).show();
            }
            //Request Permission
            requestPermissions(new String []{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PHOTO);
        }
    }

    public void endActivityButton (){
        final ImageView addPhoto = (ImageView) findViewById(R.id.addPhoto);

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
                Bitmap bitmap = ((BitmapDrawable)addPhoto.getDrawable()).getBitmap();
                saveImageToInternalStorage(bitmap, contact);

                //Pass Data back
                Intent intent = new Intent();
                intent.putExtra(CONTACT_CLASS, contact);
                setResult(Activity.RESULT_OK, intent);
                Toast.makeText(AddContact.this, "Contact Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_PHOTO){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            } else {
                Toast.makeText(this, "Gallery permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }else {
             super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_ADD_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ImageView addPhoto = (ImageView) findViewById(R.id.addPhoto);
                            addPhoto.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(AddContact.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(AddContact.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                    checkIfAttahced = false;
                }
                break;

        }
    }

    public void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_ADD_PHOTO);
    }

    public static Intent makeIntent (Context context){
        Intent intent = new Intent(context, AddContact.class);
        return intent;
    }
    public static Serializable getObject (Intent intent){
        return intent.getSerializableExtra(CONTACT_CLASS);
    }


}
