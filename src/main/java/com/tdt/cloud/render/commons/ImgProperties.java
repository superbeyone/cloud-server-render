package com.tdt.cloud.render.commons;

import lombok.Data;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className ImgProperties
 * @description
 * @date 2020-11-03 15:20
 **/

@Data
public class ImgProperties {

    private String path;

    private String height = "400";

    private String width = "400";
    
}
