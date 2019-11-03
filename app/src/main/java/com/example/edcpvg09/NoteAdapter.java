package com.example.edcpvg09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {



    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
            holder.title.setText("Title:"+model.getTitle());
            holder.desp.setText("Description:"+model.getDesp());
            holder.approvedby.setText("Approved By:"+model.getApprovedBy());
            holder.amount.setText("Amount:"+String.valueOf(model.getAmount()));
            holder.name.setText("Name:"+model.getName());
            Picasso.get().load(model.getImageurl()).into(holder.bill);
            //Picasso.with(No).load(model.getImageurl()).into(bill);
       // Picasso.with(this).load(imageUri).into(imview);
        // imview.setImageURI(imageUri);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);

        return new NoteHolder(v);
    }



    public void approved(int position){
        final DocumentSnapshot snapshot=getSnapshots().getSnapshot(position);
        Map<String, Object> approved = new HashMap<>();
        approved.put("title",snapshot.get("title"));
        approved.put("amount",Integer.parseInt(snapshot.get("amount").toString()));
        approved.put("desp",snapshot.get("desp"));
        approved.put("approvedBy",snapshot.get("approvedBy"));
        approved.put("fileName",snapshot.get("fileName"));
        approved.put("imageurl",snapshot.get("imageurl"));
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        getSnapshots().getSnapshot(position).getReference().delete();
        db.collection("2019/"+snapshot.get("email")+"/expenses").add(approved)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                            db.collection("tInfo").document("final").update("finalOwe", FieldValue.increment(Integer.parseInt(snapshot.get("amount").toString())));
//                        db.collection("2019").document(snapshot.get("email").toString()).collection("expenses").document("total").update("IndTotal",FieldValue.increment(Integer.parseInt(snapshot.get("amount").toString())));
                       // db.document("2019/"+snapshot.get("email")).update("Total",FieldValue.increment(Integer.parseInt(snapshot.get("amount").toString())));



                        DocumentReference docIdRef = db.collection("2019").document(snapshot.get("email").toString()).collection("expenses").document("total");
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        if (document.get("IndTotal") != null) {
                                            db.collection("2019").document(snapshot.get("email").toString()).collection("expenses").document("total").update("IndTotal",FieldValue.increment(Integer.parseInt(snapshot.get("amount").toString())));
                                            //Log.d(TAG, "your field exist");
                                        } else {
                                            Map<String,Object> temp = new HashMap<>();
                                            temp.put("IndTotal",Integer.parseInt(snapshot.get("amount").toString()));
                                            db.collection("2019").document(snapshot.get("email").toString()).collection("expenses").document("total").set(temp);
                                           // Log.d(TAG, "your field does not exist");
                                            //Create the filed
                                        }
                                    }
                                    else {
                                        Map<String,Object> temp = new HashMap<>();
                                        temp.put("IndTotal",Integer.parseInt(snapshot.get("amount").toString()));
                                        db.collection("2019").document(snapshot.get("email").toString()).collection("expenses").document("total").set(temp);

                                    }
                                }
                            }
                        });
                    }
                });

    }

    public void deleteItem(int position){
        DocumentSnapshot snapshot=getSnapshots().getSnapshot(position);
        final String url=snapshot.get("fileName").toString();

        System.out.println(url);
       //System.out.println( getSnapshots().getSnapshot(position).getReference().get());

        StorageReference Ref = FirebaseStorage.getInstance().getReference("uploads/"+url);

        Ref.delete();



        getSnapshots().getSnapshot(position).getReference().delete();



    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView name;
        TextView amount;
        TextView approvedby;
        TextView desp;
        ImageView bill;



    public NoteHolder(View itemview){
        super(itemview);
        title=itemview.findViewById(R.id.title);
        name=itemview.findViewById(R.id.name1);
        amount=itemview.findViewById(R.id.amount);
        approvedby=itemview.findViewById(R.id.approvedBy);
        desp=itemview.findViewById(R.id.desc1);
        bill=itemview.findViewById(R.id.bill);
    }


    }
}
