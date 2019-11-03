package com.example.edcpvg09;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class addExpense extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    EditText title;
    EditText desp;
    EditText approvedBy;
    EditText amount;
    Button mbutton ;
    Button submit;
    ProgressBar mProgress;
     int a;
    FirebaseAuth  mAuth;
    FirebaseAuth.AuthStateListener mAuthLis;
    //TextView text ;
    private Uri imageUri;
    private String URL;
    ImageView imview;
    String fileName;

    private StorageTask uploadTask;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference mStorage = FirebaseStorage.getInstance().getReference("uploads");



    @Override
    protected void onStart() {
        super.onStart();


    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        mAuth = FirebaseAuth.getInstance();
        mbutton = findViewById(R.id.button2);
        imview = findViewById(R.id.imageView);
        submit = findViewById(R.id.button3);
        title = findViewById(R.id.title);
        desp = findViewById(R.id.editText2);
        approvedBy = findViewById(R.id.editText3);
        amount = findViewById(R.id.editText4);
        mProgress = findViewById(R.id.progressBar2);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    a = Integer.parseInt(amount.getText().toString());
                    System.out.println(a);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Enter A Number in Amount ", Toast.LENGTH_LONG).show();
                    return;
                }
                if(title.getText().toString().equals("Title") && desp.getText().toString().equals("Description") && approvedBy.getText().toString().equals("Approved By") ){
                    Toast.makeText(getApplicationContext(),"Default Values",Toast.LENGTH_LONG).show();
                    return;
                }
                if(uploadTask !=null && uploadTask.isInProgress())
                {
                    Toast.makeText(getApplicationContext(),"Uploading Image!!!!",Toast.LENGTH_LONG).show();
                }else
                {
                    uploadfile();
                }


            }
        });





    }

    private String getFileEx(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadfile() {
        if(imageUri != null)
        {
            final StorageReference fileRef = mStorage.child(System.currentTimeMillis()+ "." + getFileEx(imageUri));

            uploadTask= fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler =new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setProgress(0);
                                }
                            },500);

                            Toast.makeText(getApplicationContext(),"Upload Done",Toast.LENGTH_LONG).show();

                            fileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.getResult()==null)
                                    {
                                        System.out.println(task.getResult());
                                    }else {
                                        URL = task.getResult().toString();
                                        fileName=fileRef.getName();
                                        System.out.println(title.getText().toString());
                                        System.out.println(desp.getText().toString());
                                        System.out.println(approvedBy.getText().toString());


//                   System.out.println(imageUri.toString());

                                        Map<String, Object> info = new HashMap<>();
                                        if (mAuth.getCurrentUser().getEmail() != null)
                                        {
                                            info.put("name",mAuth.getCurrentUser().getDisplayName());
                                        }else
                                        {
                                            startActivity(new Intent(addExpense.this,MainActivity.class));
                                        }

                                        info.put("title",title.getText().toString()) ;
                                        info.put("desp",desp.getText().toString());
                                        info.put("approvedBy",approvedBy.getText().toString());
                                        info.put("amount",a);
                                        System.out.println(URL);
                                        info.put("imageurl",URL);
                                        info.put("email",mAuth.getCurrentUser().getEmail());
                                        info.put("fileName",fileName);

                                        if(title.getText().toString()!="Title" && desp.getText().toString()!="Description" && approvedBy.getText().toString()!="Approved By" ){
                                            db.collection("pending")
                                                    .add(info);

                                            // startActivity(new Intent(addExpense.this,home_page.class));
                                        }else
                                        {
                                            Toast.makeText(getApplicationContext(),"Default Values",Toast.LENGTH_LONG).show();
                                        }
                                    }


                                }
                            });




                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failes To Upload",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = 100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                            mProgress.setProgress((int)progress);
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(),"No file Selected",Toast.LENGTH_LONG).show();
        }
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
                Picasso.get().load(imageUri).into(imview);
               // imview.setImageURI(imageUri);

            }
    }



}
