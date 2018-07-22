package com.example.viki.vshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public Button login,register;
    public EditText user,pass;
    private DatabaseReference myref;
    public static final String USERNAME="username";
    public static final String DATA="DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button)findViewById(R.id.loginbtn);
        register=(Button)findViewById(R.id.regbtn);
        user=(EditText)findViewById(R.id.usertext);
        pass=(EditText)findViewById(R.id.passtext);
        myref= FirebaseDatabase.getInstance().getReference("user");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=user.getText().toString();
                final String password=pass.getText().toString();
                myref.child(username).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(password.equals(snapshot.child("pass").getValue()))
                        {
                            SharedPreferences.Editor pref=getSharedPreferences(USERNAME,MODE_PRIVATE).edit();
                            pref.putString(USERNAME,username);
                            pref.apply();
                            Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),temp.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Incorrect password or username",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Reg_Avtivity.class);
                startActivity(intent);
                }
        });
    }
}
