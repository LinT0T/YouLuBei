package com.youlubei.youlubei.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.bean.SloganBean;
import com.youlubei.youlubei.ui.adapter.FavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private Button backButton, messageButton;
    private RecyclerView recyclerView;
    private List<SloganBean> res = new ArrayList<>();
    private FavoriteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            SloganBean a = new SloganBean("一个人最大的挑战，是如何去克服自己的缺点", "A person's biggest challenge, is how to overcome their own shortcomings");
            res.add(a);
            SloganBean b = new SloganBean("环境永远不会十全十美，消极的人受环境控制，积极的人却控制环境", "The environment will never be perfect, negative people are controlled by the environment, active people control the environment");
            res.add(b);
            SloganBean c = new SloganBean("没有礁石，就没有美丽的浪花；没有挫折，就没有壮丽的人生", "No rocks, there is no beautiful spray;Without setbacks, there is no magnificent life");
            res.add(c);
            SloganBean d = new SloganBean("世界上那些最容易的事情中，拖延时间最不费力", "The most easy things in the world, the delay time is the least effort");
            res.add(d);
            SloganBean e = new SloganBean("请一定要有自信。你就是一道风景，没必要在别人风景里面仰视", "Be sure to be confident.You are a landscape, there is no need to look up in the scenery of others");
            res.add(e);
            SloganBean f = new SloganBean("穷则思变，差则思勤！没有比人更高的山没有比脚更长的路", "Poverty leads to change; poor leads to diligence!There is no mountain higher than man and no road longer than foot");
            res.add(f);
            SloganBean g = new SloganBean("体悟每天都是生命最好的一刻，才能算是了解人生的人", "To realize that every day is the best moment of life is to be a person who understands life");
            res.add(g);
        }
    }

    private void initView() {
        backButton = findViewById(R.id.tab_back_button);
        messageButton = findViewById(R.id.message_button);
        recyclerView = findViewById(R.id.favorite_recycler);
        backButton.setOnClickListener(v -> finish());
        mAdapter = new FavoriteAdapter(res);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}