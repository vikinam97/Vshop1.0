package com.example.viki.vshop;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class additem extends AppCompatActivity {

    public final int pick_image=1;
    public Uri imageuri;
    public String imgdownurl,id;
    private ImageView image;
    private ProgressBar progress;

    private Button publish;
    private EditText tvname,tvdesp,tvprice;

    private SharedPreferences pref;
    private StorageReference imageref;
    private DatabaseReference prdref;

    private StorageTask utask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        publish=(Button)findViewById(R.id.publish_item);
        tvname=(EditText)findViewById(R.id.prdname);
        tvdesp=(EditText)findViewById(R.id.prddesp);
        image=(ImageView)findViewById(R.id.image);
        tvprice=(EditText)findViewById(R.id.txtprice);
        progress=(ProgressBar)findViewById(R.id.progress);

        imageref= FirebaseStorage.getInstance().getReference("user_image");
        prdref= FirebaseDatabase.getInstance().getReference("Product");


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileopener();
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(utask!=null && utask.isInProgress())
                {   Toast.makeText(getApplicationContext(),"uploading in progress",Toast.LENGTH_SHORT).show();
                }
                else
                {   uploadfile();
                }
            }
        });

    }

    public void fileopener()
    {   Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pick_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==pick_image && resultCode == RESULT_OK && data !=null && data.getData() !=null)
        {
                imageuri=data.getData();
                image.setImageURI(imageuri);
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));

    }
    public void uploadfile()
    {       if(imageuri!=null)
            {
                StorageReference fileref=imageref.child(System.currentTimeMillis()+"."+getFileExtension(imageuri));
                utask=fileref.putFile(imageuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler hand=new Handler();
                                hand.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.setProgress(0);
                                    }
                                },500);
                                Toast.makeText(getApplicationContext(),"image uploaded successfully",Toast.LENGTH_SHORT).show();
                                imgdownurl=taskSnapshot.getDownloadUrl().toString();
                                pref=getSharedPreferences(MainActivity.USERNAME,MODE_PRIVATE);
                                String username=pref.getString(MainActivity.USERNAME,null);
                                String name=tvname.getText().toString();
                                String desp=tvdesp.getText().toString();
                                String price=tvprice.getText().toString();
                                //Toast.makeText(getApplicationContext(), "User = "+username, Toast.LENGTH_SHORT).show();
                                id=prdref.push().getKey();
                                Product prd=new Product(id,username,name,desp,imgdownurl,price);
                                prdref.child(id).setValue(prd);
                                //System.out.println(prd.getUsername()+" "+prd.getName()+" "+prd.getDesp()+" "+prd.getImageurl() );*/
                                onBackPressed();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"upload error",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double pro=(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                progress.setProgress((int) pro);
                            }
                        });

            }
            else
            {
                Toast.makeText(getApplicationContext(),"image not selected",Toast.LENGTH_SHORT).show();
            }

    }
}
