package com.youlubei.youlubei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youlubei.youlubei.bean.RvBean;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private Context context;
    private List<RvBean> mList;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private IonSlidingViewClickListener mISetBtnClickListener;

    private LeftSlideView mMenu = null;

    public RvAdapter(Context context, List<RvBean> mList) {
        this.context = context;
        this.mList = mList;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        mISetBtnClickListener = (IonSlidingViewClickListener) context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mList != null) {
//            holder.title.setText(mList.get(position).getTitle());
            holder.content.setText(mList.get(position).getContent());
            holder.layout.getLayoutParams().width = Utils.getScreenWidth(context);
//            //item正文点击事件
//            holder.textView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //判断是否有删除菜单打开
//                    if (menuIsOpen()) {
//                        closeMenu();//关闭菜单
//                    } else {
//                        int n = holder.getLayoutPosition();
//                        mIDeleteBtnClickListener.onItemClick(v, n);
//                    }
//
//                }
//            });


            //左滑设置点击事件
            holder.set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = holder.getLayoutPosition();
                    mISetBtnClickListener.onSetBtnCilck(view, n);
                }
            });


            //左滑删除点击事件
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onDeleteBtnCilck(view, n);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        //        private TextView title;
        private TextView content;
        private TextView set;
        private TextView delete;
        private ViewGroup layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.img_item);
            set = itemView.findViewById(R.id.tv_set);
            delete = itemView.findViewById(R.id.tv_delete);
            layout = itemView.findViewById(R.id.layout_content);
            content = itemView.findViewById(R.id.tv_content_item);
            ((LeftSlideView) itemView).setSlidingButtonListener(RvAdapter.this);
        }
    }


    /**
     * 删除item
     *
     * @param position
     */
    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }


    /**
     * 滑动或者点击了Item监听
     *
     * @param leftSlideView
     */
    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断菜单是否打开
     *
     * @return
     */
    public Boolean menuIsOpen() {
        return mMenu != null;
    }


    /**
     * 注册接口的方法：点击事件。在Mactivity.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);//点击item正文

        void onDeleteBtnCilck(View view, int position);//点击“删除”

        void onSetBtnCilck(View view, int position);//点击“设置”
    }

}
