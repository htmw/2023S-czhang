package com.example.gsorting.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsorting.R;
import com.example.gsorting.bean.Rubbish;
import com.example.gsorting.ui.activity.RubbishDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RubbishAdapter extends RecyclerView.Adapter<RubbishAdapter.ViewHolder> {
    private List<Rubbish> list;//数据
    private Context mContext;
    private ItemListener mItemListener;
    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    public RubbishAdapter(){
        this.list = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_rubbish_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rubbish rubbish = list.get(position);
        if (rubbish != null) {
            String title =String.format("%s(%s)",rubbish.getName(),rubbish.getType());
            String content= rubbish.getContent();
            holder.tvTitle.setText(title);//设置标题
            holder.tvContent.setText(content);//设置内容
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RubbishDetailsActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("content",content);
                    mContext.startActivity(intent);
                }
            });
            holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener!=null){
                        mItemListener.ItemClick(rubbish);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemListener!=null){
                        mItemListener.ItemLongClick(rubbish);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Rubbish> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;//标题
        private TextView tvContent;//内容
        private ImageView ivUpdate;//修改
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            ivUpdate = itemView.findViewById(R.id.iv_update);
        }
    }
    public interface ItemListener{
        void ItemClick(Rubbish rubbish);
        void ItemLongClick(Rubbish rubbish);
    }
}
