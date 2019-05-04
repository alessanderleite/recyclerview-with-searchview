package br.com.alessanderleite.recyclerviewwithsearchview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.alessanderleite.recyclerviewwithsearchview.R;
import br.com.alessanderleite.recyclerviewwithsearchview.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {

    private List<Item> exampleList;
    private List<Item> exampleListFull;
    private Context context;


    public ItemAdapter(List<Item> exampleList, List<Item> exampleListFull, Context context) {
        this.exampleList = exampleList;
        this.exampleListFull = new ArrayList<>(exampleList);
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {
        Item item = exampleList.get(i);

        holder.mLogin.setText(item.getLogin());
        holder.mHtmlUrl.setText(item.getHtmlUrl());

        Glide.with(context)
                .load(item.getAvatarUrl())
                .into(holder.mAvatar);
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mLogin;
        private TextView mHtmlUrl;
        private ImageView mAvatar;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mLogin = (TextView) itemView.findViewById(R.id.txt_login);
            mHtmlUrl = (TextView) itemView.findViewById(R.id.txt_html_url);
            mAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : exampleListFull) {
                    if (item.getLogin().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
