package com.example.admin.trumpet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private GestureDetectorCompat gestureObject;
    //private Button mdata;
    int p = 0;
    public int z = 0; //why is this here
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mfirebasedatabase;
    private static final String TAG = "ProfileActivity";
    private FirebaseAuth.AuthStateListener mauthlistener;
    private DatabaseReference myref;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        mfirebasedatabase = FirebaseDatabase.getInstance();
        myref=mfirebasedatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
    }

    //region swipe
    @Override
    public boolean onTouchEvent (MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY){

            if(event2.getX() > event1.getX()){
                //left to right swipe
                Intent intent = new Intent (ProfileActivity.this, biopage.class);
                finish();
                startActivity(intent);
            }

            else
            if (event2.getX() < event1.getX()){
                //right to left swipe
            }
            return true;
        }
    }
    //endregion




    private void update(final int p) {
        mfirebasedatabase = FirebaseDatabase.getInstance();
        myref = mfirebasedatabase.getReference();
        FirebaseUser user =firebaseAuth.getCurrentUser();

        String userID = user.getUid();
        myref.child("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {


                dataSnapshot.getRef().child("points").setValue(p);
                //dialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Users", databaseError.getMessage());
            }
        });
    }


    //this calls on database class
    public void data(View view){
        Intent flash = new Intent(this, Database.class);
        startActivity(flash);}

    public void plus(View view){
        p++;disp(p);}

    public void minus(View view){
        p--;disp(p);}

    public void logout(View view){
        firebaseAuth.signOut();}

    private void disp(int p) {
        TextView ok= (TextView) findViewById(R.id.point);
        ok.setText("" + p);
    update(p);}}