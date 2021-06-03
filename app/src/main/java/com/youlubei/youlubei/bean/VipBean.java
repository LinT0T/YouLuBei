package com.youlubei.youlubei.bean;

public class VipBean {
    private String vip_time;
    private String vip_price;

    public VipBean(String time, String price) {
        this.vip_time = time;
        this.vip_price = price;
    }

    public String getVip_time() {
        return vip_time;
    }

    public String getVip_price() {
        return vip_price;
    }
}
