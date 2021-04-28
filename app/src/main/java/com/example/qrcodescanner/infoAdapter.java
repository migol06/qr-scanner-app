package com.example.qrcodescanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.List;

public class infoAdapter extends FirebaseRecyclerAdapter<uploadInformation, infoAdapter.Holder > {
    List<Integer> list = new ArrayList<Integer>();


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
                uploadInformation.setSelected(!uploadInformation.getSelected());
                //holder.view.setBackgroundColor(uploadInformation.getSelected() ? Color.LTGRAY : Color.WHITE);

                if(uploadInformation.getSelected()){
                    list.add(position);

                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                            MenuInflater menuInflater = actionMode.getMenuInflater();
                            menuInflater.inflate(R.menu.listdeletemenu, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                            holder.view.setBackgroundColor(Color.LTGRAY);
                            holder.ivCheckbox.setVisibility(View.VISIBLE);
                            holder.imageView.setVisibility(View.INVISIBLE);
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            int id = menuItem.getItemId();
                            switch (id){
                                case R.id.delete:

                                    for (int shit : list){
                                        FirebaseDatabase.getInstance().getReference().child("Information")
                                                .child(getRef(shit).getKey()).removeValue();
                                    }
                                    actionMode.finish();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {

                        }
                    };
                    ((AppCompatActivity) view.getContext()).startActionMode(callback);
                } else{
                    holder.view.setBackgroundColor(Color.WHITE);
                    holder.ivCheckbox.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.VISIBLE);
                }

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
        private ImageView imageView, ivCheckbox;
        private View view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.txtTime);
            info = itemView.findViewById(R.id.txtInfo);
            imageView = itemView.findViewById(R.id.recyclerImage);
            view = itemView;
            ivCheckbox = itemView.findViewById(R.id.ivCheckBox);

        }
    }
}
