package com.example.edcpvg09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tMasterHome extends AppCompatActivity {

   FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button logs;
    Button search ;



    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() !=null)
        {
            final TextView owed = findViewById(R.id.totalOwe);
            db.collection("tInfo").document("final")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("", "DocumentSnapshot data: " + document.getData());

                                    owed.setText("Total Debt = "+ document.get("finalOwe").toString());
                                } else {
                                    Log.d("", "No such document");
                                }
                            } else {
                                Log.d("", "get failed with ", task.getException());
                            }
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_master_home);
        logs=findViewById(R.id.pLogs);
        logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(tMasterHome.this,pendingEx.class));
            }
        });
        search=findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
