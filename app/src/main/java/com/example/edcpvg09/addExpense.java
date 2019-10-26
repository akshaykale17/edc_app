package com.example.edcpvg09;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.net.URI;


public class addExpense extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    //EditText edit1;
    //EditText edit2;
    Button mbutton ;
    //TextView text ;
    private Uri imageUri;
    ImageView imview;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        mbutton = findViewById(R.id.button2);
        imview = findViewById(R.id.imageView);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });
    }

    private void openFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
               imageUri = data.getData();
               System.out.println(imageUri);
                Picasso.with(this).load(imageUri).into(imview);
               // imview.setImageURI(imageUri);

            }
    }
}
