package com.youlubei.youlubei.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.bean.SloganBean;
import com.youlubei.youlubei.ui.FavoriteActivity;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<SloganBean> res;

    public FavoriteAdapter(List<SloganBean> beans) {
        this.res = beans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SloganBean sloganBean = res.get(position);
        holder.cSlogan.setText(sloganBean.getcSlogan());
        holder.eSlogan.setText(sloganBean.geteSlogan());
    }

    @Override
    public int getItemCount() {
        return res.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cSlogan, eSlogan;

        public ViewHolder(View view) {
            super(view);
            cSlogan = view.findViewById(R.id.cSlogan);
            eSlogan = view.findViewById(R.id.eSlogan);
        }
    }
}
