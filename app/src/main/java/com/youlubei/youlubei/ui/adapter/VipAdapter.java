package com.youlubei.youlubei.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.bean.VipBean;
import com.youlubei.youlubei.ui.OpenVipActivity;

import java.util.List;

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {

    private List<VipBean> res;
    private OpenVipActivity openVipActivity;
    private OnItemClickListener onRecyclerViewItemClickListener;
    private int thisPosition;

    public VipAdapter(OpenVipActivity activity, List<VipBean> res) {
        this.openVipActivity = activity;
        this.res = res;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(openVipActivity);
        View view = inflater.inflate(R.layout.vip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VipBean vipBean = res.get(position);
        holder.year.setText(vipBean.getVip_time());
        holder.price.setText(vipBean.getVip_price());
        if (position == getThisPosition()) {
            holder.background.setImageDrawable(ResourcesCompat.getDrawable(openVipActivity.getResources(), R.color.pink, null));
        } else {
            holder.background.setImageDrawable(ResourcesCompat.getDrawable(openVipActivity.getResources(), R.color.white, null));
        }

        if (onRecyclerViewItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                onRecyclerViewItemClickListener.onClick(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return res.size();
    }

    public int getThisPosition() {
        return thisPosition;
    }

    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public String getPrice(int thisPosition) {
        return res.get(thisPosition).getVip_price();
    }

    public void setOnRecyclerViewItemClickListener(OnItemClickListener onItemClickListener) {
        this.onRecyclerViewItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onClick(int position);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView year, price;
        ImageView background;

        public ViewHolder(View view) {
            super(view);
            year = view.findViewById(R.id.vip_time);
            price = view.findViewById(R.id.vip_price);
            background = view.findViewById(R.id.vip_background);
        }
    }
}
