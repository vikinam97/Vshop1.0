package com.example.viki.vshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reg_Avtivity extends AppCompatActivity {

    private EditText name,mail,phone,pass;
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg__avtivity);

        final DatabaseReference myref= FirebaseDatabase.getInstance().getReference("user");

        name=(EditText)findViewById(R.id.regname);
        mail=(EditText)findViewById(R.id.regmail);
        phone=(EditText)findViewById(R.id.regpno);
        pass=(EditText)findViewById(R.id.regpass);
        reg=(Button)findViewById(R.id.regbtn);

        reg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String tname=name.getText().toString();
                String tmail=mail.getText().toString();
                String tphone=phone.getText().toString();
                String tpass=pass.getText().toString();



                if (!(TextUtils.isEmpty(tname)&&TextUtils.isEmpty(tmail)&&TextUtils.isEmpty(tphone)&&TextUtils.isEmpty(tpass)))
                {
                    final User newuser=new User(tname,tphone,tmail,tpass);
                    final DatabaseReference userNameRef = myref.child(tname);
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()) {

                                userNameRef.setValue(newuser);
                                Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"already exists ",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    };
                    userNameRef.addListenerForSingleValueEvent(eventListener);
                }
            }
        });
    }
}
