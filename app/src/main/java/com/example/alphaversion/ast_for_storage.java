package com.example.alphaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import android.os.Bundle;

public class ast_for_storage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText FileName;
    ImageView imageView;
    ProgressBar imageProg;

    Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    FirebaseStorage storage;
    FirebaseDatabase database;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ast_for_storage);

        FileName = (EditText) findViewById(R.id.FileName);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageProg = (ProgressBar) findViewById(R.id.imageProg);


        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("Uploads");

        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference("Upload");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    public void auth(MenuItem item) {
        startActivity(new Intent(ast_for_storage.this, MainActivity.class));
    }

    public void storage(MenuItem item) {
        startActivity(new Intent(ast_for_storage.this, FirebaseStorage.class));
    }

    // actions are set as "Select image" button is pressed
    public void SelectImage(View view) {
        openFileChooser();
    }

    // actions are set as "Show uploads" button is pressed
    public void Showupload(View view) {
        startActivity(new Intent(ast_for_storage.this, ImagesActivity.class));
    }

    // actions are set as "Upload Image" button is pressed
    public void uploadImage(View view) {
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(ast_for_storage.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        }
        else{
            uploadFile();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imageView);
        }
    }

    // get extension from file (file type)
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    // creation of unique File name in order to make sure not to override
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageProg.setProgress(0);

                                }
                            }, 500);

                            Toast.makeText(ast_for_storage.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            imageProg.setVisibility(View.GONE);


                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            //Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString()); //use if testing...don't need this line.
                            Upload upload = new Upload(FileName.getText().toString().trim(),downloadUrl.toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);


                            /*
                            Upload upload = new Upload(FileName.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                            */
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ast_for_storage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            imageProg.setVisibility(View.VISIBLE);
                            imageProg.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}