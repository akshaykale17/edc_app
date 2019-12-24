package com.example.edcpvg09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class seeApprovedExpense extends AppCompatActivity {

    private TextView name;
    private TextView Amount;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    CollectionReference notebook = db.collection("2019/"+mAuth.getCurrentUser().getEmail()+"/expenses");
    NoteFinalAdapter adapter;
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

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
        setRecycle();



    }

    private void setRecycle() {
        Query query = notebook.orderBy("title");
        FirestoreRecyclerOptions<NoteFinal> options1=new FirestoreRecyclerOptions.Builder<NoteFinal>()
                .setQuery(query,NoteFinal.class)
                .build();

        adapter=new NoteFinalAdapter(options1);


        RecyclerView recyclerView = findViewById(R.id.approvedRe);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);




    }



    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
