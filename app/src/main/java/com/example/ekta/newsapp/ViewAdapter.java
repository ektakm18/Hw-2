package com.example.ekta.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ekta.newsapp.model.NewsItem;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder>{

    private List<NewsItem> items;
    private Context context;
    private ItemClickListener listener;

    public ViewAdapter(List<NewsItem> items, Context context, ItemClickListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsItem item = items.get(position);
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewPublishedAt.setText(item.getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPublishedAt;


        public ViewHolder(View itemView) {

            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            textViewPublishedAt = (TextView) itemView.findViewById(R.id.publishedAt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}