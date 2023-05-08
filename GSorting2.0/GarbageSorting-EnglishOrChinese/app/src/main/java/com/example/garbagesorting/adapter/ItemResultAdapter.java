package com.example.garbagesorting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagesorting.R;
import com.example.garbagesorting.bean.ItemResult;
import com.example.garbagesorting.bean.Rubbish;
import com.example.garbagesorting.ui.activity.RubbishDetailsActivity;
import com.example.garbagesorting.util.translate.TransApi;

import java.util.ArrayList;
import java.util.List;

public class ItemResultAdapter extends RecyclerView.Adapter<ItemResultAdapter.ViewHolder> {
    private List<ItemResult> list;//数据
    private Activity mContext;
    private ItemListener mItemListener;

    public void setItemListener(ItemListener itemListener) {
        this.mItemListener = itemListener;
    }

    public ItemResultAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = (Activity) parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_item_result_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemResult itemResult = list.get(position);
        if (itemResult != null) {
            holder.itemName.setText(itemResult.getItemName());
            holder.itemCategory.setText(itemResult.getItemCategory());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<ItemResult> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd != null) {
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;//
        private TextView itemCategory;//

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemCategory = itemView.findViewById(R.id.itemCategory);
        }
    }

    public interface ItemListener {
        void ItemClick(Rubbish rubbish);

        void ItemLongClick(Rubbish rubbish);
    }
}
