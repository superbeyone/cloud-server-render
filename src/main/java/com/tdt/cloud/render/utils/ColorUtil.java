package com.tdt.cloud.render.utils;

import java.util.Random;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className ColorUtil
 * @description
 * @date 2020-11-05 17:20
 **/

public class ColorUtil {

    static final String[] colorArr =
            new String[]{"#37A2DA", "#32C5E9", "#67E0E3", "#9FE6B8", "#FFDB5C", "#ff9f7f", "#fb7293",
                    "#E062AE", "#E690D1", "#e7bcf3", "#9d96f5", "#8378EA", "#96BFFF",
                    "#c23531", "#2f4554", "#61a0a8", "#d48265", "#91c7ae", "#749f83", "#ca8622", "#bda29a", "#6e7074", "#546570", "#c4ccd3",
                    "#dd6b66", "#759aa0", "#e69d87", "#8dc1a9", "#ea7e53", "#eedd78", "#73a373", "#73b9bc", "#7289ab", "#91ca8c", "#f49f42"
            };


    public static String getColor() {
        Random random = new Random();
        int index = random.nextInt(colorArr.length);
        return colorArr[index];
    }

}
