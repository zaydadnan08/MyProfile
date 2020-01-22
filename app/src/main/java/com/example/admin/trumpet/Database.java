package com.example.admin.trumpet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Database extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mfirebasedatabase;
    private static final String TAG = "Database";
    private FirebaseAuth.AuthStateListener mauthlistener;
    private DatabaseReference myref;
    private String userID;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.database);
        firebaseAuth = FirebaseAuth.getInstance();
        mfirebasedatabase = FirebaseDatabase.getInstance();
        myref=mfirebasedatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID=user.getUid();
        mListView = (ListView)findViewById(R.id.listview);

        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:"+user.getUid());
                    toastMessage("Succesfully signed in with:"+user.getEmail());
                }
                else{
                    //user is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Succesfully signed out");
                }

            }
        };
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            datainfo uinfo = new datainfo();
            uinfo.setname(ds.child(userID).getValue(datainfo.class).getname());//set name
            uinfo.setcoin(ds.child(userID).getValue(datainfo.class).getcoin());// set coin adress
            uinfo.setEmail(ds.child(userID).getValue(datainfo.class).getEmail());//set email
            uinfo.setpoints(ds.child(userID).getValue(datainfo.class).getpoints());//set email

            Log.d(TAG, "showData: name" + uinfo.getname());
            Log.d(TAG, "showData: email" + uinfo.getEmail());
            Log.d(TAG, "showData: coin" + uinfo.getcoin());

            ArrayList<String> array = new ArrayList<>();
            array.add(uinfo.getname());
            array.add(uinfo.getcoin());
            array.add(uinfo.getEmail());
            array.add(uinfo.getpoints());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);

        }
    }

    @Override
    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mauthlistener);}

    @Override
    public void onStop(){
        super.onStop();
        if (mauthlistener!=null) {
            firebaseAuth.removeAuthStateListener(mauthlistener);}}

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();}

}

