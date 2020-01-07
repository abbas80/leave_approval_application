package com.example.db;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class mentorapplication extends AppCompatActivity {
    private DatabaseReference db,db1,db2,db3;
    private ListView listapps;
    private String name,key,validkey_ment="invalid";
    private ArrayAdapter<String> adapter;
    String arr[];
    Dialog grant;
    ment r;
    long last_size=0;
    Button accept,reject;
    ImageView close;
    TextView leaveaps;
    private ArrayList<String> apps=new ArrayList<>();
    int f=0,c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentorapplication);
        grant=new Dialog(this);
        listapps = (ListView) findViewById(R.id.list);
        c++;
        Intent intent = getIntent();
        name = intent.getStringExtra("email");

        //g_apps();
        db1 = FirebaseDatabase.getInstance().getReference().child("mentor");

        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(f==0)
                {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                         r = child.getValue(ment.class);
                        key = child.getKey().toString();
                        String rname = r.getNa();
                        //Toast.makeText(getApplicationContext(), "found list =" + l1, Toast.LENGTH_LONG).show();
                        ArrayList<String> p1 = new ArrayList<String>(r.getStringList());

                        // String ar[]=new String[apps.size()];


                        if (rname.equalsIgnoreCase(name) ) {
                            final String realkey=child.getKey().toString();
                            final String getna=r.getNa();
                            final String getment=r.getMent();
                            final int getph= r.getPh();
                            validkey_ment=realkey;
                            final List<String> l1 = new ArrayList<String>(r.getStringList());
                            last_size=l1.size();

                            Toast.makeText(getApplicationContext(),"f="+f,Toast.LENGTH_SHORT).show();


                            Toast.makeText(getApplicationContext(), "found list  --" + l1, Toast.LENGTH_LONG).show();

                            //l1.remove(0);
                           // l1.add("check out");
                            //l1.add("check outti");
                            apps = new ArrayList<String>(l1);
                            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.applist, R.id.ap1, apps);
                            listapps.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                           // final ment o=new ment(r.getNa(),r.getMent(),l1,r.getPh());
                            db1.removeEventListener(this);
                            if(validkey_ment.equalsIgnoreCase("invalid")==false)
                            {
                                db = FirebaseDatabase.getInstance().getReference().child("mentor").child(validkey_ment).child("stringList");
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        List<String> lk=new ArrayList<>();
                                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                                            lk.add(child.getValue().toString());
                                        }
                                            //ment kl=dataSnapshot.getValue(ment.class);
                                            long s4=dataSnapshot.getChildrenCount();
                                        if (s4>last_size) {
                                                NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(mentorapplication.this, "specified")
                                                        .setSmallIcon(R.drawable.bt)
                                                        .setContentTitle("hurry")
                                                        .setContentText("your first notification"+lk)
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mentorapplication.this);
                                                notificationManagerCompat.notify(1, mbuilder.build());
                                               apps= new ArrayList<String>(lk);
                                            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.applist, R.id.ap1, apps);
                                            listapps.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();

                                        }
                                            last_size=dataSnapshot.getChildrenCount();

                                        }
                                    //}

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            //DatabaseReference df = FirebaseDatabase.getInstance().getReference("mentor").child(key);
                            //df.setValue(o);

                            listapps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    List<String> l2 = new ArrayList<String>(apps);
                                    l2.remove(position);

                                    Toast.makeText(getApplicationContext(), "key  --" +realkey, Toast.LENGTH_LONG).show();

                                    Toast.makeText(getApplicationContext(), "removed list  --" + l2, Toast.LENGTH_LONG).show();

                                    ment ol=new ment(getna,getment,l2,getph);

                                    dialogg(apps.get(position), position, realkey,ol);

                                    Toast.makeText(getApplicationContext(), "" + apps.get(position), Toast.LENGTH_LONG).show();

                                }
                            });

                            //  Toast.makeText(getApplicationContext(), "adapter list breeak --" + apps, Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getApplicationContext(), "adapter list --" + apps, Toast.LENGTH_LONG).show();
       /* if(validkey_ment.equalsIgnoreCase("invalid")==false)
        {
            db = FirebaseDatabase.getInstance().getReference().child("mentor").child(validkey_ment);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                    if (c >= 1) {
                            NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(mentorapplication.this, "specified")
                                    .setSmallIcon(R.drawable.bt)
                                    .setContentTitle("hurry")
                                    .setContentText("your first notification")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mentorapplication.this);
                            notificationManagerCompat.notify(1, mbuilder.build());
                    }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }*/
    }

  /*  public void g_apps() {
        int f = 0;
        db1 = FirebaseDatabase.getInstance().getReference().child("mentor");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ment r = child.getValue(ment.class);
                    String rname = r.getNa();
                    List<String> l1 = new ArrayList<String>(r.getStringList());
                    Toast.makeText(getApplicationContext(), "found list =" + l1, Toast.LENGTH_LONG).show();
                    //apps=new ArrayList<String>(l1);
                    // String ar[]=new String[apps.size()];
                    if (rname.equalsIgnoreCase(name)) {
                        String ar[] = new String[l1.size()];
                        //ar=apps.toArray(ar);
                        for (int i = 0; i < l1.size(); i++) {
                            apps.add(l1.get(i));
                        }
                        Toast.makeText(getApplicationContext(), "found list --" + apps, Toast.LENGTH_LONG).show();

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
    public void dialogg(final String contentt, final int i, final String k1, final ment op)
    {
        grant.setContentView(R.layout.dialog_grant);
        accept=(Button)grant.findViewById(R.id.accept);

        reject=(Button)grant.findViewById(R.id.reject);

        close=(ImageView) grant.findViewById(R.id.close);
        leaveaps=(TextView)grant.findViewById(R.id.leave_aps);
        leaveaps.setText(contentt);
        final int eq=i;
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"accepted",Toast.LENGTH_LONG).show();
                DatabaseReference dfk = FirebaseDatabase.getInstance().getReference("mentor").child(k1);
                dfk.setValue(op);
                apps.remove(i);

                adapter.notifyDataSetChanged();
                final DatabaseReference dp=FirebaseDatabase.getInstance().getReference().child("user");

                dp.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d:dataSnapshot.getChildren())
                        {
                            artist r=d.getValue(artist.class);
                            final String key=d.getKey().toString();
                            String msgcpmp=r.getMsg();
                            Toast.makeText(getApplicationContext(),"id="+r.getNa(),Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(),"msg="+r.getMsg(),Toast.LENGTH_LONG).show();
                            String content=contentt;
                            Toast.makeText(getApplicationContext(),"con="+content,Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"con="+msgcpmp.equalsIgnoreCase(content),Toast.LENGTH_SHORT).show();
                            if((msgcpmp.trim()).equalsIgnoreCase(content.trim()))
                            {
                               // dp.removeEventListener(this);
                                f=1;
                                r.setF(1);
                                Toast.makeText(getApplicationContext(),"in="+r.getNa(),Toast.LENGTH_LONG).show();
                                artist k=new artist(r.getNa(),r.getPh(),msgcpmp,r.getMentor(),1);
                                db3=FirebaseDatabase.getInstance().getReference().child("user").child(key);
                                db3.setValue(k);
                                db3.removeEventListener(this);
                                /* final DatabaseReference dm=FirebaseDatabase.getInstance().getReference().child("mentor");
                                dm.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //    f++;
                                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                String j = d.getKey().toString();
                                                Toast.makeText(getApplicationContext(), "" + d.getKey().toString(), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "" + k1.toString(), Toast.LENGTH_SHORT).show();

                                                if ((d.getKey().toString()).equals(k1)) {


                                                  /*  Toast.makeText(getApplicationContext(), "" + (d.getKey().toString()).equals(k1), Toast.LENGTH_SHORT).show();

                                                    ment temp = d.getValue(ment.class);

                                                    List<String> tempar = new ArrayList<String>(temp.getStringList());
                                                    //dm.removeEventListener(this);
                                                    //tempar.remove(i);

                                                    //temp.setStringList(tempar);
                                                    //ment temp1 = new ment(temp.getNa(), temp.ment, temp.getStringList(), temp.getPh());
                                                    Log.i("item removed... ", "item removed...");
                                                    //tempar.remove(i);
                                                    //  adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.applist, R.id.ap1,tempar);
                                                    //apps.remove(i);
                                                    //eq=i;
                                                    Toast.makeText(getApplicationContext(), "removers"+i, Toast.LENGTH_SHORT).show();

                                                    tempar.remove(i);

                                                    apps=new ArrayList<String>(tempar);
                                                    //listapps.setAdapter(adapter);
                                                    //adapter.clear();

                                                    ArrayAdapter<String> adapteer = new ArrayAdapter<String>(getApplicationContext(), R.layout.applist, R.id.ap1, apps);
                                                    listapps.setAdapter(adapteer);
                                                    adapteer.notifyDataSetChanged();

                                                   //dm.removeEventListener(this);
                                                    //dp.removeEventListener(this);
                                                   // ment o=new ment(temp.getNa(),temp.getMent(),tempar,temp.getPh());
                                                     //DatabaseReference df = FirebaseDatabase.getInstance().getReference("mentor").child(k1);
                                                    //df.setValue(o);
                                                    break;
                                                }
                                                //dm.setValue(temp1);
                                            }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });*/


                                grant.dismiss();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"rejected",Toast.LENGTH_LONG).show();
                DatabaseReference dfk = FirebaseDatabase.getInstance().getReference("mentor").child(k1);
                dfk.setValue(op);
                apps.remove(i);
                adapter.notifyDataSetChanged();


                db2=FirebaseDatabase.getInstance().getReference().child("user");
                db2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            artist r=dataSnapshot1.getValue(artist.class);
                            String key=dataSnapshot1.getKey().toString();
                            Toast.makeText(getApplicationContext(),"id="+r.getNa(),Toast.LENGTH_SHORT).show();
                            String msgcpmp=r.getMsg().toString();
                           // String msgcpmp=r.getMsg();
                            Toast.makeText(getApplicationContext(),"id="+r.getNa(),Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(),"msg="+r.getMsg(),Toast.LENGTH_LONG).show();
                            String content=contentt;
                            Toast.makeText(getApplicationContext(),"con="+content,Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"con="+msgcpmp.equalsIgnoreCase(content),Toast.LENGTH_SHORT).show();
                            if(msgcpmp.equals(content))
                            {
                                r.setF(-1);
                                Toast.makeText(getApplicationContext(),"in="+r.getNa(),Toast.LENGTH_LONG).show();

                                artist k=new artist(r.getNa(),r.getPh(),"",r.getMentor(),-1);
                                db3=FirebaseDatabase.getInstance().getReference().child("user").child(key);
                                db3.setValue(k);
                             /*  final   DatabaseReference dm=FirebaseDatabase.getInstance().getReference().child("mentor").child(key);
                                dm.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        ment temp=dataSnapshot.getValue(ment.class);
                                        ArrayList<String> tempar=new ArrayList<>(temp.getStringList());

                                        tempar.remove(i);
                                        temp.setStringList(tempar);
                                        dm.setValue(temp);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });*/
                                //apps.remove(i);
                                //adapter.notifyDataSetChanged();
                                grant.dismiss();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getApplicationContext(),"rejected",Toast.LENGTH_LONG).show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grant.dismiss();
            }
        });
        grant.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        grant.show();

    }
    public void update(final String j, ment temp)
    {
        ment o=new ment(temp.getNa(),temp.getMent(),temp.getStringList(),temp.getPh());
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("mentor").child(j);
        df.setValue(o);
        /*df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                df.child(j).removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        //df.setValue(p);

    }
}
