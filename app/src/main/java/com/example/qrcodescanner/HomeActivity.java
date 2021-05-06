package com.example.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private CardView carviewScan, cardViewList, cardViewLogout;
    private DatabaseReference dbref;
    private FirebaseAuth mAuth;
    private  static  long fepoch;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setTitle("Information Scanner");

        dbref= FirebaseDatabase.getInstance().getReference("Information");
        mAuth = FirebaseAuth.getInstance();

        carviewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQr();
            }
        });


        cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, listActivity.class);
                startActivity(intent);
            }
        });


        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void scanQr() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(HomeActivity.this);
        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.itemshome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.registerUser:
                Intent intent = new Intent(this, signupActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        final String currentTime = dateFormat.format(today);


        try{
            Date date = dateFormat.parse(currentTime);
            long epochTime = date.getTime();
            fepoch = epochTime;
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        if (intentResult.getContents() != null){
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String data = intentResult.getContents();
                    if (data.contains("|")){
                        uploadInformation information = new uploadInformation(currentTime,data, fepoch);
                        dbref.child(dbref.push().getKey()).setValue(information);
                        dialogInterface.dismiss();
                    }else{
                        Snackbar.make(parent, "Invalid Information, Please try again", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scanQr();
                                    }
                                })
                                .setActionTextColor(Color.RED)
                                .show();
                    }

                }
            });
            builder.show();
        }else{
            Snackbar.make(parent, "No information scanned", Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    private void initViews() {
        carviewScan=findViewById(R.id.cardViewScan);
        cardViewList=findViewById(R.id.cardViewList);
        cardViewLogout=findViewById(R.id.cardViewLogout);
        parent = findViewById(R.id.homeparent);

    }
}