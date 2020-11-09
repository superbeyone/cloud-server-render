package com.tdt.cloud.render.entities;

import lombok.Data;

@Data
public class BarParam {

    private Object[] barName = new Object[]{"a", "b", "c"};

    private Object[] barValue = new Object[]{10, 20, 30};

    private String legendName = "test";

    //省略get/set方法

}
