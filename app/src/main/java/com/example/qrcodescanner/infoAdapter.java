package com.example.qrcodescanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

public class infoAdapter extends FirebaseRecyclerAdapter<uploadInformation, infoAdapter.Holder > {

    public infoAdapter(@NonNull FirebaseRecyclerOptions<uploadInformation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull infoAdapter.Holder holder, int position, @NonNull uploadInformation uploadInformation) {

        holder.time.setText(uploadInformation.getTime());

        holder.info.setText(uploadInformation.getInfo());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.view.getContext());
                builder.setTitle("Information");

                StringBuilder builder1 = new StringBuilder();
                builder1.append(uploadInformation.getTime() + "\n" + uploadInformation.getInfo());

                builder.setMessage(builder1);
                builder.show();
            }
        });
        
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.view.getContext());
                builder.setTitle("Delete Information");
                builder.setMessage("Are you sure to delete this information?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Information")
                                .child(getRef(position).getKey()).setValue(null);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();


                return true;
            }
        });


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
        private View view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.txtTime);
            info = itemView.findViewById(R.id.txtInfo);
            imageView = itemView.findViewById(R.id.recyclerImage);
            view = itemView;

        }
    }
}
