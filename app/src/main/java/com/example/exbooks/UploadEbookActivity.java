package com.example.exbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadEbookActivity extends AppCompatActivity {


    ImageView imagebrowse,imageupload,filelogo,cancelfile;
    Uri filepath;

    EditText filetitle;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload_ebook);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("mydocuments");

        filetitle=findViewById(R.id.filetitle);

        imagebrowse=findViewById(R.id.imagebrowse);
        imageupload=findViewById(R.id.imageupload);

        filelogo=findViewById(R.id.filelogo);
        cancelfile=findViewById(R.id.cancelfile);


        filelogo.setVisibility(View.INVISIBLE);
        cancelfile.setVisibility(View.INVISIBLE);

        cancelfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filelogo.setVisibility(View.INVISIBLE);
                cancelfile.setVisibility(View.INVISIBLE);
                imagebrowse.setVisibility(View.VISIBLE);
            }
        });




    }
}