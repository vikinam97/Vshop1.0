package com.example.viki.vshop;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class temp extends AppCompatActivity {


    private Button additem;

    private ListView rcview;
    private producr_adapter padapter;
    private SharedPreferences pref;

    private DatabaseReference myref;
    private DatabaseReference userref;
    private List<Product> products;
    private Product curr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        additem=(Button)findViewById(R.id.additembtn);
        rcview=(ListView) findViewById(R.id.rview);


        myref= FirebaseDatabase.getInstance().getReference("Product");
        userref= FirebaseDatabase.getInstance().getReference("user");

        products=new ArrayList<>();

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products.clear();
                for(DataSnapshot post:dataSnapshot.getChildren())
                {   Product p=post.getValue(Product.class);
                    products.add(p);
                }
                padapter=new producr_adapter(temp.this,products);
                rcview.setAdapter(padapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent additem=new Intent(getApplicationContext(),additem.class);
                startActivity(additem);
            }
        });

        rcview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {   curr=products.get(position);
                String seller=curr.getUsername();
                userref.child(seller).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                     User sell=snapshot.getValue(User.class);
                        pref=getSharedPreferences(MainActivity.USERNAME,MODE_PRIVATE);
                        String username=pref.getString(MainActivity.USERNAME,null);
                        Intent intent=new Intent(getApplicationContext(),detail.class);

                        intent.putExtra("mail",sell.getMail());
                        intent.putExtra("username",username);
                        intent.putExtra("product_name",curr.getName());
                        intent.putExtra("desp",curr.getDesp());
                        intent.putExtra("price",curr.getPrice());
                        intent.putExtra("imageurl",curr.getImageurl());

                        startActivity(intent);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

    }
}
