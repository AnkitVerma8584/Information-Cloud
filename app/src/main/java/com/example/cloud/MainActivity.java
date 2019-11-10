package com.example.cloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        auth= FirebaseAuth.getInstance();
        progressBar=new ProgressDialog(this);
        reg=findViewById(R.id.reg);
        e=findViewById(R.id.email);
        p=findViewById(R.id.pas);
        tv=findViewById(R.id.textview);
        reg.setOnClickListener(this);
        tv.setOnClickListener(this);
        if(auth.getCurrentUser()!=null){
            finish();
            Intent i=new Intent(MainActivity.this,Information.class);
            startActivity(i);
        }

    }
    @Override
    public void onClick(View view)
    {
        if(view == reg)
        {
            registerUser();
        }
        if(view == tv)
        {
            Intent i=new Intent(this,Login.class);
            startActivity(i);
            finish();
        }
    }
    private void registerUser()
    {
        String email=e.getText().toString().trim();
        String password=p.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Fields cannot be Blank",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Fields cannot be Blank",Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setMessage("Registering user...");
        progressBar.show();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Could not register...Please try again!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alt=new AlertDialog.Builder(this);
        alt.setTitle("Attention!")
                .setCancelable(false)
                .setMessage("Are you sure you want to exit?")
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
