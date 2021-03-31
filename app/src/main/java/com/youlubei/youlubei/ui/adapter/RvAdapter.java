package com.youlubei.youlubei.ui.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.youlubei.youlubei.utils.LeftSlideView;
import com.youlubei.youlubei.R;
import com.youlubei.youlubei.utils.SharedPreferenceUtil;
import com.youlubei.youlubei.utils.Utils;
import com.youlubei.youlubei.bean.RvBean;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private Context context;
    private List<RvBean> mList;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private IonSlidingViewClickListener mISetBtnClickListener;
    private IonSlidingViewClickListener ionSlidingViewClickListener;

    private LeftSlideView mMenu = null;

    public RvAdapter(Context context, List<RvBean> mList) {
        this.context = context;
        this.mList = mList;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        mISetBtnClickListener = (IonSlidingViewClickListener) context;
        ionSlidingViewClickListener = (IonSlidingViewClickListener) context;
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
            holder.line.setVisibility(View.INVISIBLE);
            RvBean rvBean = mList.get(position);
//            holder.title.setText(rvBean.getTitle());
            holder.content.setText(rvBean.getContent());
            holder.layout.getLayoutParams().width = Utils.getScreenWidth(context);
            if (rvBean.isFinish()) {
                switch (rvBean.getFinishColor()){
                    case 0:
                        holder.layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.roug_word_finish_background, null));
                        break;
                    case 1:
                        holder.layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.roug_read_finish_background, null));
                        break;
                    case 2:
                        holder.layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.roug_study_finish_background, null));
                        break;
                    case 3:
                        holder.layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.roug_sport_finish_background, null));
                        break;
                }

                holder.set.setVisibility(View.GONE);
                TranslateAnimation animation = new TranslateAnimation(Animation.ABSOLUTE, -(Utils.getScreenWidth(context) + 150),
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f);
                animation.setDuration(1000);
                animation.setInterpolator(new BounceInterpolator());
                holder.content.setTextColor(context.getResources().getColor(R.color.finish));
                holder.imageView.setImageResource(R.drawable.ic_finish);
                ValueAnimator vValue = ValueAnimator.ofFloat(1.0f, 1.5f, 0.5f, 1.2f, 0.8f, 1.0f);
                vValue.setDuration(1000L);
                vValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float scale = (Float) animation.getAnimatedValue();
                        holder.imageView.setScaleX(scale);
                        holder.imageView.setScaleY(scale);
                    }
                });

                if (rvBean.getLastPosition() == mList.size() - 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.line.setVisibility(View.VISIBLE);
                            holder.line.startAnimation(animation);
                            vValue.start();
                        }
                    }, 300 + 120);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.line.setVisibility(View.VISIBLE);
                            holder.line.startAnimation(animation);
                            vValue.start();
                        }
                    }, 300 + 120 + 250);
                }

                holder.delete.setText("恢复");
            } else {
                holder.layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.roug_background, null));
                holder.content.setTextColor(context.getResources().getColor(R.color.white));
                holder.set.setVisibility(View.VISIBLE);
                holder.delete.setText("完成");
                holder.imageView.setImageResource(R.mipmap.background);
            }

            //item正文点击事件
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //判断是否有删除菜单打开
                    if (menuIsOpen()) {
                        closeMenu();//关闭菜单
                    } else {
                        int n = holder.getLayoutPosition();
                        mIDeleteBtnClickListener.onItemClick(v, n);
                    }

                }
            });


            //左滑设置点击事件
            holder.set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = holder.getLayoutPosition();
                    mISetBtnClickListener.onSetBtnCilck(view, n);
                }
            });


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onDeleteBtnCilck(v, n, mList.get(position).isFinish());
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
        private View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item);
            set = itemView.findViewById(R.id.tv_set);
            delete = itemView.findViewById(R.id.tv_delete);
            layout = itemView.findViewById(R.id.layout_content);
            content = itemView.findViewById(R.id.tv_content_item);
            line = itemView.findViewById(R.id.line_item);
            ((LeftSlideView) itemView).setSlidingButtonListener(RvAdapter.this);
        }
    }


    /**
     * 删除item
     *
     * @param position
     */
    public void removeData(int position) {

        RvBean rvBean = mList.get(position);
        rvBean.setFinish(!rvBean.isFinish());
        rvBean.setLastPosition(position);
        mList.add(rvBean);
        mList.remove(position);
        notifyItemRemoved(position);
        if (position != mList.size()) {
            notifyItemRangeChanged(0, mList.size() );
        }
        for (RvBean a:mList
             ) {
            if (!a.isFinish()){
                return;
            }
        }
        ionSlidingViewClickListener.onAllFinish();
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

    public void saveData(){
        SharedPreferenceUtil.getInstance().put(context,"data0",new Gson().toJson(mList.get(0)));
        SharedPreferenceUtil.getInstance().put(context,"data1",new Gson().toJson(mList.get(1)));
        SharedPreferenceUtil.getInstance().put(context,"data2",new Gson().toJson(mList.get(2)));
        SharedPreferenceUtil.getInstance().put(context,"data3",new Gson().toJson(mList.get(3)));
    }


    /**
     * 注册接口的方法：点击事件。在Mactivity.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);//点击item正文

        void onDeleteBtnCilck(View view, int position, boolean isFinish);//点击“删除”

        void onSetBtnCilck(View view, int position);//点击“设置”

        void onAllFinish();//所有任务完成
    }

}
