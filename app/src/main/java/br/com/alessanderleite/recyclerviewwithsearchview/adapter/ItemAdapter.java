package br.com.alessanderleite.recyclerviewwithsearchview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import br.com.alessanderleite.recyclerviewwithsearchview.R;
import br.com.alessanderleite.recyclerviewwithsearchview.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
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
        Item item = itemList.get(i);

        holder.mLogin.setText(item.getLogin());
        holder.mHtmlUrl.setText(item.getHtmlUrl());

        Glide.with(context)
                .load(item.getAvatarUrl())
                .into(holder.mAvatar);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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
}
