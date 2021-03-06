package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.adapters.player.PopupHandler;

import java.util.List;

public class RepliesViewDelegate implements AdapterDelegate<List<?>> {

    private PopupHandler popupHandler;

    private int viewType;

    public RepliesViewDelegate(PopupHandler popupHandler, int viewType) {
        this.popupHandler = popupHandler;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new RepliesViewHolder(itemView, popupHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof RepliesViewHolder) {
            ((RepliesViewHolder) holder).bindRepliesView(position, items);
        }
    }

    @Override
    public int getViewType() {
        return viewType;
    }
}
