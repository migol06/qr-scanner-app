package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class listActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private infoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("Lists");

        initViews();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        FirebaseRecyclerOptions<uploadInformation> options = new
                FirebaseRecyclerOptions.Builder<uploadInformation>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Information"), uploadInformation.class)
                .build();

        adapter = new infoAdapter(options);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
    }
}