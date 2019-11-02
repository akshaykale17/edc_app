package com.example.edcpvg09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

    public void deleteItem(int position){
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
