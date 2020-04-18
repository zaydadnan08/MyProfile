package com.example.admin.trumpet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewEmail, textViewName;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewEmail = itemView.findViewById(R.id.textViewEmail);
        textViewName = itemView.findViewById(R.id.textViewName);


    }
}
