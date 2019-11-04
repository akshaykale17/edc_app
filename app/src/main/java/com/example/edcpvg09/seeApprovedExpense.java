package com.example.edcpvg09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class seeApprovedExpense extends AppCompatActivity {

    private TextView name;
    private TextView Amount;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db =FirebaseFirestore.getInstance();


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() !=null)
        {
            Amount=findViewById(R.id.textView4);
            name=findViewById(R.id.nameText);
            name.setText(mAuth.getCurrentUser().getDisplayName());
            db.collection("2019").document(mAuth.getCurrentUser().getEmail()).collection("expenses").document("total")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("", "DocumentSnapshot data: " + document.getData());

                                    Amount.setText("Total Amount = "+ document.get("IndTotal").toString());
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
        setContentView(R.layout.activity_see_approved_expense);




    }
}
