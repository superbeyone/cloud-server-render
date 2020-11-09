package com.tdt.cloud.render.entities;

import lombok.Data;

@Data
public class BarData {

    private String title = "superbeyone";  //标题

    private BarParam barParamList = new BarParam();

    private Boolean isHorizontal = true;  //是否水平放置

    //省略get/set方法


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BarParam getBarParamList() {
        return barParamList;
    }

    public void setBarParamList(BarParam barParamList) {
        this.barParamList = barParamList;
    }

    public Boolean getHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(Boolean horizontal) {
        isHorizontal = horizontal;
    }
}
