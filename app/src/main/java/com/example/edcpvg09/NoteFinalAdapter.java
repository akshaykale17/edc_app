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

public class NoteFinalAdapter extends FirestoreRecyclerAdapter<NoteFinal, NoteFinalAdapter.NoteHolder> {

    public NoteFinalAdapter(@NonNull FirestoreRecyclerOptions<NoteFinal> options) {
        super(options);
    }


//    public NoteFinalAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
//        super(options);
//    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull NoteFinal model) {
        holder.title.setText("Title:"+model.getTitle());
        holder.desp.setText("Description:"+model.getDesp());
        holder.approvedby.setText("Approved By:"+model.getApprovedBy());
        holder.amount.setText("Amount:"+String.valueOf(model.getAmount()));

        Picasso.get().load(model.getImageurl()).into(holder.bill);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);

        return new NoteFinalAdapter.NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView title;

        TextView amount;
        TextView approvedby;
        TextView desp;
        ImageView bill;



        public NoteHolder(View itemview){
            super(itemview);
            title=itemview.findViewById(R.id.title);

            amount=itemview.findViewById(R.id.amount);
            approvedby=itemview.findViewById(R.id.approvedBy);
            desp=itemview.findViewById(R.id.desc1);
            bill=itemview.findViewById(R.id.bill);
        }


    }
}
