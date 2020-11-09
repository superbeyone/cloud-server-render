package com.tdt.cloud.render.service;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className PolygonService
 * @description
 * @date 2020-11-03 15:14
 **/

public interface PolygonService {

    /**
     * 返回面数据
     *
     * @param data json数据
     * @param dir 父级目录
     * @return 导出路径
     */
    String getPolygonPath(String data,String dir);
}
