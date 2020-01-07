package com.example.db;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class application extends AppCompatActivity {
    private EditText t1;
    private TextView mymsg,status;
    private artist wp,up;
    private Button b1;
    private int c=0;
    private String name;
    private String msg,key,mentor,kk,mo;
    private FirebaseAuth auth;
    private DatabaseReference db,db1,db3;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        t1=(EditText)findViewById(R.id.t1);
        b1=(Button)findViewById(R.id.b1);
        status=findViewById(R.id.status);
        mymsg=(TextView)findViewById(R.id.my_msg);

        final DatabaseReference v1=FirebaseDatabase.getInstance().getReference().child("user");
        v1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent =getIntent();
                String email=intent.getStringExtra("email");

                for (DataSnapshot fd:dataSnapshot.getChildren())
                {
                    artist n=fd.getValue(artist.class);
                    if(n.getNa().equals(email))
                    {

                        mymsg.setText(n.getMsg());
                        if(n.getF()==1)
                        {
                            mymsg.setBackgroundResource(R.drawable.accepted);
                            status.setText("Accepted");
                           // status.setTextColor(Color.parseColor("#59FF5C"));
                            //status.setTextColor(R.color.accepted);
                        }
                        else if(n.getF()==-1)
                        {
                            mymsg.setBackgroundResource(R.drawable.rejected);
                            status.setText("Rejected");
                            status.setTextColor(Color.parseColor("#FF2C2C"));
                            //status.setTextColor(R.color.rejected);
                        }
                        else
                        {
                            status.setText("Pending");
                        }
                        v1.removeEventListener(this);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        db= FirebaseDatabase.getInstance().getReference().child("user");



        DatabaseReference v=FirebaseDatabase.getInstance().getReference().child("user");

        v.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                artist g=dataSnapshot.getValue(artist.class);
                Intent intent =getIntent();
                String email=intent.getStringExtra("email");
                if(g.getF()==1&&(g.getNa().equals(email)))
                {
                    mymsg.setBackgroundResource(R.drawable.accepted);
                    status.setTextColor(Color.parseColor("#59FF5C"));
                    status.setText("Accepted");
                    NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(application.this, "specified")
                            .setSmallIcon(R.drawable.bt)
                            .setContentTitle("hurry")
                            .setContentText("accepted your first notification")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(application.this);
                    notificationManagerCompat.notify(1, mbuilder.build());
                }
                else if(g.getF()==-1&&(g.getNa().equals(email)))
                {
                    mymsg.setBackgroundResource(R.drawable.rejected);
                    status.setText("Rejected");

                    status.setTextColor(Color.parseColor("#FF2C2C"));
                    NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(application.this, "specified")
                            .setSmallIcon(R.drawable.bt)
                            .setContentTitle("hurry")
                            .setContentText("rejected your first notification")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(application.this);
                    notificationManagerCompat.notify(1, mbuilder.build());

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 c=0;
                msg=t1.getText().toString().trim();
                db.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ar:dataSnapshot.getChildren())
                        {
                            if(c==1) {
                                break;
                            }
                            key=ar.getKey().toString();
                            Log.i("key",key);
                            ///Toast.makeText(application.this,"key=="+key,Toast.LENGTH_LONG).show();
                            wp=ar.getValue(artist.class);
                            String name1=wp.getNa();
                            mentor=wp.getMentor().toString().toLowerCase();
                            Intent intent =getIntent();
                            name=intent.getStringExtra("email");
                            Log.i("naa",name);

                            if(name.equalsIgnoreCase(name1)&&c!=1)
                            {

                                mo=mentor;
                                Date calendar=Calendar.getInstance().getTime();
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");
                                String m1=msg+"\nThanking you\n"+name+"\n"+simpleDateFormat.format(calendar);
                                 artist up=new artist(name,wp.getPh(),m1,mentor,0);
                         //       List<String> m=new ArrayList<String>();
                                db1= FirebaseDatabase.getInstance().getReference().child("user").child(key);
                                db1.setValue(up);
                                mymsg.setText(m1);
                                status.setText("Pending");
                                mymsg.setBackgroundResource(R.drawable.lt);
                                //mymsg.setBackgroundResource(R.drawable.accepted);
                                DatabaseReference db2=FirebaseDatabase.getInstance().getReference().child("mentor");
                                // ment mm = new ment("fgdf","3434",223,msg);
                                //db2.push().setValue(mm);

                                db2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for(DataSnapshot aar:dataSnapshot.getChildren())
                                        {
                                            //mentorr mentorr=aar.getValue(mentorr.class);

                                                ment r = aar.getValue(ment.class);
                                                String kk = aar.getKey().toString();
                                                Log.i("key mentor", kk);
                                                Toast.makeText(getApplicationContext(),"key mentor=="+wp.getNa()+"mo="+mo,Toast.LENGTH_SHORT).show();
                                                String mentor1=r.getMent().toString();
                                                String naa=r.getNa().toString();
                                                List<String> ll=r.getStringList();
                                                //r.getStringList();
                                                int phh=r.getPh();
                                                if(c==1)
                                                {
                                                    break;
                                                }
                                                if(mentor1.toString().equals(mo)&&c<1)//mentor.equalsIgnoreCase("android"))
                                                {
                                                    Date calendar=Calendar.getInstance().getTime();
                                                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");
                                                    msg=msg+"\nThanking you\n"+name+"\n"+simpleDateFormat.format(calendar);

                                                    ll.add(msg);
                                                    c++;
                                                    Toast.makeText(getApplicationContext(),"key mentor added",Toast.LENGTH_SHORT).show();
                                                    ment rr=new ment(naa,mentor1,ll,phh);
                                                   // artist up=new artist(name,wp.getPh(),msg,mentor,0);
                                                 //   up.setMsg(msg);
                                                    db3 = FirebaseDatabase.getInstance().getReference().child("mentor").child(kk);
                                                    db3.setValue(rr);
                                                //db3 = FirebaseDatabase.getInstance().getReference().child("mentor").child(kk).child("msg");
                                                    break;
//                                                db3.push().setValue(msg);
                                            }/*if((m.g
                                            Ment().toString()).equalsIgnoreCase(mentor)) {
                                                //List<String> mrs = new ArrayList<String>();
                                                 kk=ar.getKey().toString();
                                                String mrs=msg;
                                               // String mento = m.getMent().toString();
                                                //mrs.add(msg+"klpf");
                                                //ment k=new ment()
                                                ment mm = new ment(m.getNa(),m.getMent().toString(),m.getPh(),msg);
                                                db3 = FirebaseDatabase.getInstance().getReference().child("mentor").child(kk);
                                                db3.setValue(mm);

                                            }*/
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                }) ;
                               // db2.push().setValue(msg);*/
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
