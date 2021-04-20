package com.example.qrcodescanner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.core.Tag;

public class infoAdapter extends FirebaseRecyclerAdapter<uploadInformation, infoAdapter.Holder > {

    public infoAdapter(@NonNull FirebaseRecyclerOptions<uploadInformation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull infoAdapter.Holder holder, int i, @NonNull uploadInformation uploadInformation) {
        holder.time.setText(uploadInformation.getTime());
        holder.info.setText(uploadInformation.getInfo());
    }

    @NonNull
    @Override
    public infoAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recyclerview,parent,false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView time, info;
        private ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.txtTime);
            info = itemView.findViewById(R.id.txtInfo);
            imageView = itemView.findViewById(R.id.recyclerImage);

        }
    }
}
