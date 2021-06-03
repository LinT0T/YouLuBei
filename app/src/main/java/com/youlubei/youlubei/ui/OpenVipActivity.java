package com.youlubei.youlubei.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.bean.VipBean;
import com.youlubei.youlubei.ui.adapter.VipAdapter;
import com.youlubei.youlubei.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class OpenVipActivity extends AppCompatActivity {

    private final List<VipBean> res = new ArrayList<>();
    VipAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vip);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        VipBean a = new VipBean("连续包年", "¥148");
        res.add(a);
        VipBean b = new VipBean("连续包季", "¥45");
        res.add(b);
        VipBean c = new VipBean("连续包月", "¥15");
        res.add(c);
        VipBean d = new VipBean("年度会员", "¥168");
        res.add(d);
        VipBean e = new VipBean("季度会员", "¥68");
        res.add(e);
        VipBean f = new VipBean("月度会员", "¥25");
        res.add(f);
    }

    private void initView() {
        ImageView back = findViewById(R.id.vip_back_image);
        RecyclerView recyclerView = findViewById(R.id.vip_recycler_view);
        back.setOnClickListener(v -> finish());
        mAdapter = new VipAdapter(this, res);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        Button payButton = findViewById(R.id.pay_button);
        mAdapter.setOnRecyclerViewItemClickListener(position -> {
            payButton.setText("立即以" + mAdapter.getPrice(position) + "开通");
            mAdapter.setThisPosition(position);
            mAdapter.notifyDataSetChanged();

        });
        RadioButton rd1 = findViewById(R.id.rd_bt_1);
        RadioButton rd2 = findViewById(R.id.rd_bt_2);
        rd2.setOnClickListener(v -> {
            rd1.setChecked(false);
        });
        payButton.setOnClickListener(v -> {
            Utils.showToast(this, "暂未开放");
        });
        ImageView back = findViewById(R.id.vip_back_image);
        back.setOnClickListener(v -> {
            finish();
        });
    }
}