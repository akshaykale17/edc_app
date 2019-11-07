package com.example.edcpvg09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class home_page extends AppCompatActivity {
    Button button2 ;
    Button approved;
    FirebaseAuth mAuth;
    String temail;
    FirebaseAuth.AuthStateListener mAuthListner;
    int id;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    Button addEx ;
//    Button addBunk = findViewById(R.id.addBunk);
//    Button seeEx = findViewById(R.id.seeApprovedEx);
//    Button seeBunk = findViewById(R.id.seeApprovedBunk);
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    String name;
    TextView textView1 ;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        if(mAuth.getCurrentUser() != null)
        {
            name = mAuth.getCurrentUser().getDisplayName();
            textView1 = findViewById(R.id.username);
            textView1.setText(name);
        }
        else
        {
            startActivity(new Intent(home_page.this,MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addEx = findViewById(R.id.addExpense);
        addEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_page.this,addExpense.class));
            }
        });

        approved = findViewById(R.id.seeApprovedEx);
        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_page.this,seeApprovedExpense.class));
            }
        });

        button2 = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(home_page.this,MainActivity.class));
                }
            }
        };

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         id=item.getItemId();
        db.collection("tInfo").document("temail")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("", "DocumentSnapshot data: " + document.getData());

                                temail=document.get("email").toString();
                                System.out.println(temail);



                                if(id==R.id.master) {
                                    System.out.println(mAuth.getCurrentUser().getEmail());
                                    if (mAuth.getCurrentUser().getEmail().equals(temail)) {
                                        startActivity(new Intent(home_page.this,tMasterHome.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authorized Personnel Only :)", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                Log.d("", "No such document");
                            }
                        } else {
                            Log.d("", "get failed with ", task.getException());
                        }

                    }
                });


        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
            //System.exit(0);
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}
