package com.tatvasoft.expansemangement.ui.category.model;

import java.io.Serializable;

public class DetailsModel implements Serializable {
    private String moneySpend;
    private String remark;
    private String category;
    private String date;

    public DetailsModel(String moneySpend, String remark, String category, String date) {
        this.moneySpend = moneySpend;
        this.remark = remark;
        this.category = category;
        this.date = date;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
