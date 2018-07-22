package com.example.viki.vshop;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class producr_adapter extends ArrayAdapter<Product> {
    private Activity mcontext;
    private List<Product> products;

    public producr_adapter(Activity mcontext, List<Product> products) {
        super(mcontext,R.layout.product_card,products);
        this.mcontext = mcontext;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent)
    {
        LayoutInflater inflator =mcontext.getLayoutInflater();
        View listviewitem=inflator.inflate(R.layout.product_card,null,true);



        ImageView pimage=(ImageView)listviewitem.findViewById(R.id.downimage);
        TextView pprice=(TextView)listviewitem.findViewById(R.id.prdprice);
        TextView pname=(TextView)listviewitem.findViewById(R.id.prdname);

        Product pro=products.get(position);

        pname.setText(pro.getName());

        pprice.setText(pro.getPrice());

        Picasso.with(mcontext)
                .load(pro.getImageurl())
                .centerCrop().fit()
                .into(pimage);

        return listviewitem;

    }






}
