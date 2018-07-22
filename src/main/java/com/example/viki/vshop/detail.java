package com.example.viki.vshop;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class detail extends AppCompatActivity {

    private ImageView image;
    private TextView name,desp,price;
    private Button buy;

    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image=(ImageView)findViewById(R.id.image1);
        name=(TextView)findViewById(R.id.name1);
        desp=(TextView)findViewById(R.id.desp1);
        price=(TextView)findViewById(R.id.price1);
        buy=(Button)findViewById(R.id.buy1);
        webview=(WebView)findViewById(R.id.webview1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });


        Intent intent=getIntent();

        final String mail=intent.getStringExtra("mail");
        final String username=intent.getStringExtra("username");
        final String proname=intent.getStringExtra("product_name");
        String sdesp=intent.getStringExtra("desp");
        final String sprice=intent.getStringExtra("price");
        String imageurl=intent.getStringExtra("imageurl");

        name.setText(proname);
        desp.setText(sdesp);
        price.append(sprice);

        Picasso.with(detail.this)
                .load(imageurl)
                .centerCrop()
                .fit()
                .into(image);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg="name  "+proname+"  price  "+sprice;
                Log.i("SendMailActivity", "Send Button Clicked.");

                String toEmails =mail;
                List<String> toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = "Vshop notofication";
                String emailBody = "Product is requested by "+username+"\n Product name "+proname+" \n Price "+sprice;
                new SendMailTask(detail.this).execute("goku.vignesh9@gmail.com",
                        "vikinam97", toEmailList, emailSubject, emailBody);
                //webview.loadUrl("https://smsapi.engineeringtgr.com/send/?Mobile=8675338072&Password=vikiraj18&Message="+msg+"&To="+num+"&Key=goku.VnjhvtydbI7AxX6wK18DH2up");
                //Toast.makeText(detail.this, "Request sent", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
