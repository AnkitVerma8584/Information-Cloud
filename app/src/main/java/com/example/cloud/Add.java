package com.example.cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.SaveInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference fd;
    private FirebaseAuth auth;
    private EditText name,phone,address;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();
        btn=findViewById(R.id.button);
        name=findViewById(R.id.editText1);
        phone=findViewById(R.id.editText2);
        address=findViewById(R.id.editText3);
        auth=FirebaseAuth.getInstance();
        String s="New"+(auth.getCurrentUser().getEmail().substring(0,auth.getCurrentUser().getEmail().indexOf('@')));
        fd=FirebaseDatabase.getInstance().getReference().child(s);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,Login.class));
        }
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn){
            save();
        }

    }
    private void save()
    {
        String n=name.getText().toString().trim();
        String p=phone.getText().toString().trim();
        String a=address.getText().toString().trim();
        AddInf s=new AddInf(n,p,a);
        fd.push().setValue(s);
        Toast.makeText(this,"Information Saved...",Toast.LENGTH_SHORT).show();
        name.getText().clear();
        phone.getText().clear();
        address.getText().clear();
    }
    @Override
    public void onBackPressed(){
        finish();
        Intent i=new Intent(Add.this,Information.class);
        startActivity(i);
    }
    }

