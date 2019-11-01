package com.example.edcpvg09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home_page extends AppCompatActivity {
    Button button2 ;
    FirebaseAuth mAuth;

    FirebaseAuth.AuthStateListener mAuthListner;

    Button addEx ;
//    Button addBunk = findViewById(R.id.addBunk);
//    Button seeEx = findViewById(R.id.seeApprovedEx);
//    Button seeBunk = findViewById(R.id.seeApprovedBunk);

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

//        addBunk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(home_page.this,addExpense.class);
//            }
//        });
//
//        seeBunk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(home_page.this,addExpense.class);
//            }
//        });
//
//        seeEx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(home_page.this,addExpense.class);
//            }
//        });

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
}
