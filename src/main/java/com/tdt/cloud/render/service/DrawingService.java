package com.tdt.cloud.render.service;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className DrawingFactory
 * @description
 * @date 2020-11-03 15:50
 **/

public interface DrawingService {

    /**
     * 制图
     *
     * @param json json数据
     * @param dir 父级目录
     * @return 生成的图片路径
     */
    String drawing(String json,String dir);
}
