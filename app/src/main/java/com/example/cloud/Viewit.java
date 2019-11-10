package com.example.cloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

public class Viewit extends AppCompatActivity {
    private TextView tv;
    int i=0;
    String s="";
    private DatabaseReference myref;
    private FirebaseAuth auth1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_viewit);
        getSupportActionBar().hide();
        tv=findViewById(R.id.text);
        final AddInf aa=new AddInf("","","");
        auth1=FirebaseAuth.getInstance();
        String s1="New"+(auth1.getCurrentUser().getEmail().substring(0,auth1.getCurrentUser().getEmail().indexOf('@')));
        myref=FirebaseDatabase.getInstance().getReference().child(s1);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i=1;
                s="";
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AddInf ai = dataSnapshot1.getValue(AddInf.class);
                    if (!ai.name.equals("")) {
                        s = s + "Name: " + ai.name + "\n";
                        s = s + "Phone: " + ai.phone + "\n";
                        s = s + "Address: " + ai.address + "\n\n";

                    }
                }
                tv.setText(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(i==0)
        {
            myref.push().setValue(aa);

        }
    }
    @Override
    public void onBackPressed(){
        finish();
        Intent i=new Intent(Viewit.this,Information.class);
        startActivity(i);
    }
}
