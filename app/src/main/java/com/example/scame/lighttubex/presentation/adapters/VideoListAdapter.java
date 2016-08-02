package com.example.scame.lighttubex.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder>{

    private List<VideoItemModel> items;
    private Context context;

    public VideoListAdapter(List<VideoItemModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.videolist_item_iv) ImageView thumbnailsIv;
        @BindView(R.id.videolist_item_title) TextView titleTv;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View videoItemView = inflater.inflate(R.layout.videolist_item, parent, false);

        return new ViewHolder(videoItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoItemModel item = items.get(position);

        ImageView imageView = holder.thumbnailsIv;
        TextView textView = holder.titleTv;

        Picasso.with(context).load("https://i.ytimg.com/vi/3AZHowhxM0U/hqdefault.jpg").into(imageView);
        textView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Context getContext() {
        return context;
    }
}
