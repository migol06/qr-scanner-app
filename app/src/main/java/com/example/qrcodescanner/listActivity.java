package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class listActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private infoAdapter adapter;
    private FirebaseRecyclerOptions<uploadInformation> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("Lists");

        initViews();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        options = new
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                orig(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                //search(s);
                return false;
            }
        });




        return super.onCreateOptionsMenu(menu);
    }

    private void orig(String s) {
        options = new FirebaseRecyclerOptions.Builder<uploadInformation>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Information")
                        .orderByChild("info").startAt(s).endAt(s + "\uf8ff"),uploadInformation.class)
                .build();

        adapter = new infoAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    private void search(String s) {
        options = new FirebaseRecyclerOptions.Builder<uploadInformation>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Information")
                .orderByChild("time").startAt(s).endAt(s + "\uf8ff"),uploadInformation.class)
                .build();

        adapter = new infoAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
}