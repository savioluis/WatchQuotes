package com.example.watchquotes;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class MainActivity2 extends AppCompatActivity {
    int qAttempts=1;
    boolean feachMovie=false;
    FirebaseFirestore db;
    String globalMovieChosen;
    String chosenGenre;
    String chosenName;
    ArrayList<String> idsMovies = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AutoCompleteTextView nameMovie = findViewById(R.id.autoCompleteMovieName);
        TextView quote1 = (TextView) findViewById(R.id.textViewQuote1);
        TextView quote2 = (TextView) findViewById(R.id.textViewQuote2);
        TextView quote3 = (TextView) findViewById(R.id.textViewQuote3);
        TextView quote4 = (TextView) findViewById(R.id.textViewQuote4);

        db=FirebaseFirestore.getInstance();

        db.collection("Movies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        String id = document.getId();
                        idsMovies.add(id);
                    }
                }
                //System.out.println("TAmanho do array de filmes:" + idsMovies.size());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_1,idsMovies);
                nameMovie.setAdapter(adapter);

                Random gerador= new Random();
                int rand = gerador.nextInt(idsMovies.size());
                globalMovieChosen= idsMovies.get(rand);


                DocumentReference docChosenMovie = FirebaseFirestore.getInstance().collection("Movies").document(globalMovieChosen);

                docChosenMovie.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            chosenGenre= documentSnapshot.getString("Genre");
                            chosenName= documentSnapshot.getString("Name");
                            String chosenQuote1= documentSnapshot.getString("Quote 1");
                            String chosenQuote2= documentSnapshot.getString("Quote 2");
                            String chosenQuote3= documentSnapshot.getString("Quote 3");
                            String chosenQuote4= documentSnapshot.getString("Quote 4");

                            quote1.setText(chosenQuote1);
                            quote2.setText(chosenQuote2);
                            quote3.setText(chosenQuote3);
                            quote4.setText(chosenQuote4);

                            /*for(int i=0;i<idsMovies.size();i++){
                                System.out.println("Filme "+i+" "+idsMovies.get(i));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_1,idsMovies);
                            nameMovie.setAdapter(adapter);*/
                            feachMovie=true;

                        }
                    }
                });
            }
        });




           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,idsMovies);
            nameMovie.setAdapter(adapter);*/


        quote1.setAlpha(0f);
        quote2.animate().alpha(0f).setDuration(0);
        quote3.animate().alpha(0f).setDuration(0);
        quote4.animate().alpha(0f).setDuration(0);

        quote1.animate().alpha(1f).setDuration(1000);


        nameMovie.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN && i==KeyEvent.KEYCODE_ENTER ){
                    movieTextBox(view);
                    return true;
                }
                return false;
            }


        });
    }


    public void movieTextBox(View v){

        //TextView nameMovie = (TextView) findViewById(R.id.autoCompleteMovieName);
        AutoCompleteTextView nameMovie = findViewById(R.id.autoCompleteMovieName);
        TextView quote1 = (TextView) findViewById(R.id.textViewQuote1);
        TextView quote2 = (TextView) findViewById(R.id.textViewQuote2);
        TextView quote3 = (TextView) findViewById(R.id.textViewQuote3);
        TextView quote4 = (TextView) findViewById(R.id.textViewQuote4);

        String nameMovieLowerCaseAndNoSpace = nameMovie.getText().toString().replaceAll(" ","").toLowerCase();
        String globalMovieChosenLowerCaseAndNoSpace= globalMovieChosen.replaceAll(" ","").toLowerCase();
        if(nameMovieLowerCaseAndNoSpace.equals(globalMovieChosenLowerCaseAndNoSpace) && qAttempts<4){ ///////////////////////////////////
            Intent i = new Intent(MainActivity2.this, MainActivity3.class);

            String strAttempts =Integer.toString(qAttempts);
            i.putExtra("qAttempts",strAttempts);
            i.putExtra("chosenName",chosenName);
            i.putExtra("chosenGenre",chosenGenre);
            supportFinishAfterTransition();
            startActivity(i);
        }else{
            if(qAttempts==1){
                quote2.animate().alpha(1f).setDuration(2000);
                qAttempts++;
            }else if(qAttempts==2){
                quote3.animate().alpha(1f).setDuration(2000);
                qAttempts++;
            }else if(qAttempts==3){
                quote4.animate().alpha(1f).setDuration(2000);
                qAttempts++;
            }else{
                if(feachMovie){
                    String strAttempts =Integer.toString(qAttempts);

                    Intent i = new Intent(MainActivity2.this, MainActivity3.class);
                    i.putExtra("qAttempts",strAttempts);
                    i.putExtra("chosenName",chosenName);
                    i.putExtra("chosenGenre",chosenGenre);
                    supportFinishAfterTransition();
                    startActivity(i);
                }
            }
        }
    }

    public void iDontKnowButton(View v){
        if(feachMovie){
            qAttempts=4;
            String strAttempts =Integer.toString(qAttempts);

            Intent i = new Intent(MainActivity2.this, MainActivity3.class);
            i.putExtra("qAttempts",strAttempts);
            i.putExtra("chosenName",chosenName);
            i.putExtra("chosenGenre",chosenGenre);
            supportFinishAfterTransition();
            startActivity(i);
        }
    }
}