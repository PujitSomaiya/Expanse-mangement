package com.tatvasoft.expansemangement.ui.category.model;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String moneySpend;
    private String remark;
    private String category;

    public DataModel( String moneySpend, String remark, String category) {
        this.moneySpend = moneySpend;
        this.remark = remark;
        this.category = category;

    }

    public String getMoneySpend() {
        return moneySpend;
    }

    public void setUserName(String moneySpend) {
        this.moneySpend = moneySpend;
    }

    //    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setBlogTag(String remark) {
        this.remark = remark;
    }


    public String getCategory() {
        return category;
    }

    public void setBlogTitle(String category) {
        this.category = category;
    }

}
