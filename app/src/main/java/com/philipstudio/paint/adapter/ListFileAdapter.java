package com.philipstudio.paint.adapter;/*
//
// Project: Paint
// Created by ViettelStore on 3/12/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philipstudio.paint.MainActivity;
import com.philipstudio.paint.R;
import com.philipstudio.paint.callback.OnItemFileClickListener;
import com.philipstudio.paint.util.LayoutUtil;

import java.io.File;
import java.util.ArrayList;

public class ListFileAdapter extends RecyclerView.Adapter<ListFileAdapter.ViewHolder> {

    ArrayList<File> arrayList;
    Context context;

    OnItemFileClickListener onItemFileClickListener;
    boolean isChangeLayout;
    LayoutUtil layoutUtil;

    public ListFileAdapter(ArrayList<File> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemFileClickListener(OnItemFileClickListener onItemFileClickListener) {
        this.onItemFileClickListener = onItemFileClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutUtil = new LayoutUtil(context);
        isChangeLayout = layoutUtil.getChangeLayout();
        View view;
        if (!isChangeLayout) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_horizontal, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_vertical, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNameFile.setText(arrayList.get(position).getName());
        Glide.with(context).load(arrayList.get(position)).into(holder.imgFile);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameFile;
        ImageView imgFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameFile = itemView.findViewById(R.id.item_name_file);
            imgFile = itemView.findViewById(R.id.item_image_file);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onItemFileClickListener != null){
                    onItemFileClickListener.onItemClick(arrayList.get(position).getAbsolutePath());
                }
            });
        }
    }
}
