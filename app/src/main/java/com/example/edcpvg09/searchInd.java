package com.example.edcpvg09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

public class searchInd extends AppCompatActivity {

    EditText search;
    Button searchbtn;

    private TextView amount ;

    CollectionReference notebook;
    NoteFinalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ind);

        amount=findViewById(R.id.searchAmount);
        search=findViewById(R.id.searchname);
        searchbtn=findViewById(R.id.searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.document("users/"+search.getText().toString().toLowerCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.get("email")==null)
                                    {
                                        Toast.makeText(searchInd.this, "Name Not Avail", Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {

                                        String Temp;
                                        //Toast.makeText(searchInd.this, "Got It", Toast.LENGTH_SHORT).show();
                                        Temp =documentSnapshot.get("email").toString();
                                        notebook = db.collection("2019/"+ Temp+"/expenses");
                                        System.out.println(Temp);

                                        db.collection("2019").document(Temp).collection("expenses").document("total")
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                Log.d("", "DocumentSnapshot data: " + document.getData());

                                                                amount.setText("Total Amount = "+ document.get("IndTotal").toString());
                                                            } else {
                                                                Log.d("", "No such document");
                                                            }
                                                        } else {
                                                            Log.d("", "get failed with ", task.getException());
                                                        }
                                                    }
                                                });



                                        Query query = notebook.orderBy("title");
                                        FirestoreRecyclerOptions<NoteFinal> options1=new FirestoreRecyclerOptions.Builder<NoteFinal>()
                                                .setQuery(query,NoteFinal.class)
                                                .build();

                                        adapter=new NoteFinalAdapter(options1);


                                        RecyclerView recyclerView = findViewById(R.id.search1);
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(searchInd.this, LinearLayoutManager.VERTICAL, false));
                                        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
                                        recyclerView.setNestedScrollingEnabled(true);
                                        recyclerView.setAdapter(adapter);




                                        adapter.startListening();


                                    }

                                }
                                else {
                                    Toast.makeText(searchInd.this, "Name Not Avail", Toast.LENGTH_SHORT).show();

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(searchInd.this, "User Name Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });


    }









    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

