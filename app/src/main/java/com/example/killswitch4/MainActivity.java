package com.example.killswitch4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1= findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1="D-307,!,830600296466,0,260,0,260,260,0,R\n" +
                        "D-411,!,830600248050,0,260,0,260,260,0,R\n" +
                        "D-412,!,8306000AY24F,0,260,0,260,260,0,R\n" +
                        "E-106,!,21591138970,10,230,70,310,310,0,R\n" +
                        "E-107,!,21591096700,0,230,0,230,200,20,R\n" +
                        "E-206,!,83060002BA34,0,230,0,230,230,40,R\n" ;

                FirebaseFirestore db= FirebaseFirestore.getInstance();
                final CollectionReference noteref= db.collection("List");
                for(String line : str1.split("\n")){
                    String [] s= line.split(",");
                    cusinfo c1= new cusinfo(s[0],s[1],s[2],Integer.parseInt(s[3]),Integer.parseInt(s[4]),Integer.parseInt(s[5]),Integer.parseInt(s[7]),s[9]);
                    Log.e("R",c1.fetchcusinfo(1));
                    c1=altercus(c1);
                    Log.e("D",c1.fetchcusinfo(1));
                    noteref.add(c1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(),"Successfully Inserted!!!",Toast.LENGTH_SHORT).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"FAILED TO INSERT!!!!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }




            }






        });
    }
    cusinfo altercus(cusinfo c1){
        c1.cash=0;
        c1.ob=c1.bal;
        c1.total=c1.ob+c1.addon+c1.pack;
        c1.bal=c1.total-c1.cash;
        return c1;
    }
}