package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private CardView carviewScan, cardViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        carviewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Scan", Toast.LENGTH_SHORT).show();
            }
        });


        cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "List", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void initViews() {
        carviewScan=findViewById(R.id.cardViewScan);
        cardViewList=findViewById(R.id.cardViewList);
    }
}