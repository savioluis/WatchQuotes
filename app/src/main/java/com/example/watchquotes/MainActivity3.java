package com.example.watchquotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.sql.SQLOutput;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {

    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            TextView textView_qAttempts = (TextView) findViewById(R.id.txt_qntAttempts);
            TextView textViewTitle=(TextView) findViewById(R.id.textView_title);
            TextView textViewGenre=(TextView) findViewById(R.id.textView_genre);

            textView_qAttempts.setAlpha(0f);
            textViewTitle.setAlpha(0f);
            textViewGenre.setAlpha(0f);

            String qAttempts = extras.getString("qAttempts");
            String chosenGenre = extras.getString("chosenGenre");
            String chosenName = extras.getString("chosenName");

            System.out.println(qAttempts);
            textView_qAttempts.setText(qAttempts);
            textViewTitle.setText(chosenName);
            textViewGenre.setText(chosenGenre);

            textView_qAttempts.animate().alpha(1f).setDuration(1000);
            textViewTitle.animate().alpha(1f).setDuration(1000);
            textViewGenre.animate().alpha(1f).setDuration(1000);

            String adaptedChosenName=chosenName.replaceAll(" ","").replaceAll("'","").toLowerCase();
            String storageChosenName= "Covers/"+adaptedChosenName+".png";
            //System.out.println(storageChosenName+" nome do filme storage");

            mStorageReference=FirebaseStorage.getInstance().getReference().child(storageChosenName);

            //mStorageReference=FirebaseStorage.getInstance().getReference().child("Covers/forrestgump.jpg");

            try{
                final File localFile=File.createTempFile(adaptedChosenName,"png");
                //final File localFile = File.createTempFile("forrestgump","jpg");
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                //Toast.makeText(MainActivity3.this,"Picture Retrieved",Toast.LENGTH_SHORT).show();
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                ((ImageView)findViewById(R.id.imageViewCover)).setImageBitmap(bitmap);


                                ImageView imageCover = (ImageView) findViewById(R.id.imageViewCover);
                                imageCover.setAlpha(0f);
                                imageCover.animate().alpha(1f).setDuration(1000);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity3.this,"Error Ocupped",Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    public void newGame(View v){
        Intent i = new Intent(MainActivity3.this, MainActivity2.class);
        supportFinishAfterTransition();
        startActivity(i);
    }
}