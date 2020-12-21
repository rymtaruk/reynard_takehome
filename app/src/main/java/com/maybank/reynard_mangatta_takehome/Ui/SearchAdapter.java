package com.maybank.reynard_mangatta_takehome.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maybank.reynard_mangatta_takehome.Model.Items;
import com.maybank.reynard_mangatta_takehome.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By reynard on 21/12/20.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<Items> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.adapter_items, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items item = items.get(position);

        holder.tvName.setText(item.getLogin());
        Picasso.get().load(item.getAvatarUrl()).placeholder(R.color.shimmer_bg).fit().centerCrop().
                into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivAvatar;
        TextView tvName;

        public ViewHolder(@NonNull View view) {
            super(view);
            ivAvatar = view.findViewById(R.id.ivAvatar);
            tvName = view.findViewById(R.id.tvName);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LayoutInflater getInflater() {
        if (inflater == null) {
            this.inflater = LayoutInflater.from(getContext());
        }
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
