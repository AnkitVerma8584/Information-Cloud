package com.example.cloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.internal.Util;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    private Button reg;
    private EditText e,p;
    private TextView tv;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        auth= FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            finish();
            Intent i=new Intent(getApplicationContext(),Information.class);
            startActivity(i);
        }
        progressBar=new ProgressDialog(this);
        reg=findViewById(R.id.reg);
        e=findViewById(R.id.email);
        p=findViewById(R.id.pas);
        tv=findViewById(R.id.textview);
        reg.setOnClickListener(this);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == reg)
        {
            Login();
        }
        if(v == tv)
        {
            finish();
            Intent i=new Intent(Login.this,MainActivity.class);
            startActivity(i);
        }
    }
    private void Login()
    {
        String email=e.getText().toString().trim();
        String password=p.getText().toString().trim();
        if(email.equals(""))
        {
           Toast.makeText(Login.this,"Enter email",Toast.LENGTH_SHORT).show();
           return;
        }
        if(password.equals(""))
        {
            Toast.makeText(Login.this,"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setMessage("Logging in...");
        progressBar.show();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.dismiss();
                            if (task.isSuccessful()) {
                                finish();
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Information.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Login.this, "Login Unsuccessful. Please Check email and password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Login.this,MainActivity.class));
    }
}
