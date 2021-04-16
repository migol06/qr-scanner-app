package com.example.qrcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private CardView carviewScan, cardViewList, cardViewLogout;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        dbref= FirebaseDatabase.getInstance().getReference("Information");

        carviewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Scan", Toast.LENGTH_SHORT).show();
                IntentIntegrator intentIntegrator = new IntentIntegrator(HomeActivity.this);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });


        cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "List", Toast.LENGTH_SHORT).show();
            }
        });


        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Date currentTime = Calendar.getInstance().getTime();
        final  String Timestamp = currentTime.toString();

        if (intentResult.getContents() != null){
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dbref.push().setValue(Timestamp + intentResult.getContents());
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }else{
            Toast.makeText(this, "You did not scan anything", Toast.LENGTH_SHORT).show();
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
    }
}