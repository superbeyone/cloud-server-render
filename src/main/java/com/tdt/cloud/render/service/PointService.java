package com.tdt.cloud.render.service;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className PointService
 * @description 点
 * @date 2020-11-04 15:51
 **/

public interface PointService {

    /**
     * 返回点数据
     *
     * @param data json数据
     * @param dir  父级目录
     * @return 导出路径
     */
    String getPointPath(String data, String dir);
}
