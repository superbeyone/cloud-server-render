package com.tdt.cloud.render.service;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className LineService
 * @description 线
 * @date 2020-11-03 15:45
 **/

public interface LineService {

    /**
     * 返回面数据
     *
     * @param data json数据
     * @param dir  父级目录
     * @return 导出路径
     */
    String getLinePath(String data, String dir);
}
