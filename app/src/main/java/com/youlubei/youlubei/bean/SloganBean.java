package com.youlubei.youlubei.bean;

public class SloganBean {
    private String cSlogan;
    private String eSlogan;

    public SloganBean(String cSlogan, String eSlogan) {
        this.cSlogan = cSlogan;
        this.eSlogan = eSlogan;
    }


    public String getcSlogan() {
        return cSlogan;
    }

    public String geteSlogan() {
        return eSlogan;
    }
}
