package com.example.db;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   private EditText t1,t2,t3;
   Boolean check;
    private Button b1,b2;
    private String mentor;
    private CheckBox admi;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(EditText)findViewById(R.id.t1);
        t2=(EditText)findViewById(R.id.t2);
        Spinner spinner=findViewById(R.id.spinner);
        admi=(CheckBox)findViewById(R.id.admi);
        check=admi.isChecked();
        /*admi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admi.isChecked())
                {

                    check=true;
                    Toast.makeText(MainActivity.this,"c="+check,Toast.LENGTH_LONG).show();
                }
                else {

                    check = false;

                    Toast.makeText(MainActivity.this,"c="+check,Toast.LENGTH_LONG).show();
                }
            }
        });*/
        t3=(EditText)findViewById(R.id.t3);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        ObjectAnimator ed1 = ObjectAnimator.ofFloat(t1, "translationX", -400f,0f);
        // animation.setInterpolator();
        ed1.setDuration(1000);
        ed1.start();


        ObjectAnimator ed2= ObjectAnimator.ofFloat(t2, "translationX", -400f,0f);
        // animation.setInterpolator();
        ed2.setDuration(1000);
        ed2.start();


        ObjectAnimator ed3 = ObjectAnimator.ofFloat(t3, "translationX", -400f,0f);
        // animation.setInterpolator();
        ed3.setDuration(1000);
        ed3.start();


        ObjectAnimator spd = ObjectAnimator.ofFloat(spinner, "translationX", -400f,0f);
        // animation.setInterpolator();
        spd.setDuration(1000);
        spd.start();
        //SystemClock.sleep(3000);
        ////////////button
        ObjectAnimator animation = ObjectAnimator.ofFloat(b1, "translationY", -200f,0f);
        // animation.setInterpolator();
        animation.setDuration(1000);
        animation.start();

        ObjectAnimator animation2 = ObjectAnimator.ofFloat(b2, "translationX", 400f,0f);
        // animation.setInterpolator();
        animation2.setDuration(1000);
        animation2.start();

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.mentor,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mentor=parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this,""+mentor,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;
        progressBar.setVisibility(View.GONE);
        admi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check=admi.isChecked();
                if(admi.isChecked())
                {

                    check=true;
                    Toast.makeText(MainActivity.this,"c="+check,Toast.LENGTH_LONG).show();
                }
                else {

                    check = false;

                    Toast.makeText(MainActivity.this,"c="+check,Toast.LENGTH_LONG).show();
                }
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();

        /*    Toast.makeText(MainActivity.this, "check user"+check, Toast.LENGTH_LONG).show();
            db = FirebaseDatabase.getInstance().getReference().child("user");
          */
        b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(check==false)
                    {
                        Toast.makeText(MainActivity.this, "check user"+check, Toast.LENGTH_LONG).show();
                        db = FirebaseDatabase.getInstance().getReference().child("user");

                        String naa = t1.getText().toString().trim();
                        String pas = t3.getText().toString().trim();
                        int phh = Integer.parseInt(t2.getText().toString().trim());
                /*artist ar=new artist(naa,phh);

                db.push().setValue(ar);*/
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(naa, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    String naa = t1.getText().toString().trim();
                                    String pas = t3.getText().toString().trim();
                                    int phh = Integer.parseInt(t2.getText().toString().trim());
                                    artist ar = new artist(naa, phh, "", mentor,0);
                                    db.push().setValue(ar).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Success in registration", Toast.LENGTH_LONG).show();
                                            } else {
                                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                    Toast.makeText(MainActivity.this, "user already registered", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "NOT Successful in registration", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(MainActivity.this, "user already registrated", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "NOT Successful in registration", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "check mentor"+check, Toast.LENGTH_LONG).show();
                        db = FirebaseDatabase.getInstance().getReference().child("mentor");
                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String naa = t1.getText().toString().trim();
                                String pas = t3.getText().toString().trim();
                                int phh = Integer.parseInt(t2.getText().toString().trim());
                /*artist ar=new artist(naa,phh);

                db.push().setValue(ar);*/
                                progressBar.setVisibility(View.VISIBLE);
                                firebaseAuth.createUserWithEmailAndPassword(naa, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            String naa = t1.getText().toString().trim();
                                            List<String> l2=new ArrayList<String>();
                                            l2.add("l");
                                            int phh = Integer.parseInt(t2.getText().toString().trim());
                                            ment r=new ment(naa,mentor,l2,phh);
                                            // mentorr ar=new mentorr(mentor,"",naa,phh);
                                            db.push().setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(MainActivity.this, "Success in MENTOR registration", Toast.LENGTH_LONG).show();
                                                    }
                                                    else {
                                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                            Toast.makeText(MainActivity.this, "user already registrated", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "NOT Successful in registration", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                        else
                                        {
                                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                Toast.makeText(MainActivity.this, "user already registrated", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                });
                            }
                        });


                    }

                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, signin.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        /*else
        {


            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, signin.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

        }*/
    public void add()
    {
        String na=t1.getText().toString().trim();
        int ph=Integer.parseInt(t2.getText().toString().trim());
        artist ar=new artist(na,ph,"",mentor,0);
        db.setValue(ar);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /*public void checkbx(View view)
    {
        if(admi.isChecked())
        {

            check=true;
            Toast.makeText(MainActivity.this,"c="+check,Toast.LENGTH_LONG).show();
        }
        else {

            check = false;

            Toast.makeText(MainActivity.this,"c="+check,Toast.LENGTH_LONG).show();
        }
    }*/
}
