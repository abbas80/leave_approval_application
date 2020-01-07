package com.example.db;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {

    private EditText t1,t2,t3;
    private Button b1,b2;
    private CheckBox admin;
    String naa;
    boolean isadmin;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db,db1;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        t1=(EditText)findViewById(R.id.t1);
       // t2=(EditText)findViewById(R.id.t2);
        admin=(CheckBox)findViewById(R.id.admin);
        //isadmin=admin.isChecked();
        t3=(EditText)findViewById(R.id.t3);
        b1=(Button)findViewById(R.id.b1);
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;
        firebaseAuth=FirebaseAuth.getInstance();
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admin.isChecked())
                {
                    isadmin=true;

                    Toast.makeText(getApplicationContext(),"isadmin= "+isadmin,Toast.LENGTH_LONG).show();
                }
                else
                {
                    isadmin=false;

                    Toast.makeText(getApplicationContext(),"isadmin= "+isadmin,Toast.LENGTH_LONG).show();
                }
            }
        });
        progressBar.setVisibility(View.GONE);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                naa=t1.getText().toString().trim();
                String pas=t3.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(naa,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            if(isadmin==true)
                            {
                               // db= FirebaseDatabase.getInstance().getReference().child("mentor");
                                Toast.makeText(signin.this," mentor successful login",Toast.LENGTH_LONG).show();
                                Intent xx=new Intent(signin.this,mentorapplication.class);
                                xx.putExtra("email",naa);
                                startActivity(xx);

                            }
                            else {
                                Toast.makeText(signin.this, "successful login", Toast.LENGTH_LONG).show();
                                Intent xx = new Intent(signin.this, application.class);
                                xx.putExtra("email", naa);
                                startActivity(xx);
                            }

                        }
                        else
                        {
                            Toast.makeText(signin.this,"unsuccessful login",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
       // db= FirebaseDatabase.getInstance().getReference().child("user");


    }
}
