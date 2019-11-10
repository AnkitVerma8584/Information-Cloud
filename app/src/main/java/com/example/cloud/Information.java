package com.example.cloud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Information extends AppCompatActivity implements View.OnClickListener {
    Button btn,add,view;
    TextView tv;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_information);
        getSupportActionBar().hide();
        auth =FirebaseAuth.getInstance();
        btn = findViewById(R.id.button2);
        tv=findViewById(R.id.text);
        FirebaseUser fu=auth.getCurrentUser();
        String email=auth.getCurrentUser().getEmail();
        tv.setText("Welcome User "+email);
        btn.setOnClickListener(this);
        add=findViewById(R.id.a);
        view=findViewById(R.id.v);
        add.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn){
            auth.signOut();
            finish();
            startActivity(new Intent(Information.this, MainActivity.class));
        }
        if(v==add){
            finish();
            startActivity(new Intent(Information.this, Add.class));
        }
        if(v==view){
            finish();
            startActivity(new Intent(Information.this, Viewit.class));
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alt=new AlertDialog.Builder(this);
        alt.setTitle("Attention!")
                .setCancelable(false)
                .setMessage("Are you sure you want to exit without signing out?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog a=alt.create();
        a.show();
    }
}
