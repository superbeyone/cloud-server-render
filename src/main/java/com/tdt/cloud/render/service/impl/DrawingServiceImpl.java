package com.tdt.cloud.render.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tdt.cloud.render.exception.CommonException;
import com.tdt.cloud.render.service.DrawingService;
import com.tdt.cloud.render.service.LineService;
import com.tdt.cloud.render.service.PointService;
import com.tdt.cloud.render.service.PolygonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className DrawingServiceImpl
 * @description
 * @date 2020-11-03 15:51
 **/

@Service
public class DrawingServiceImpl implements DrawingService {

    private final String POINT = "point";
    private final String LINE = "line";
    private final String POLYGON = "polygon";
    private final String OTHER = "other";


    @Autowired
    PointService pointService;

    @Autowired
    LineService lineService;

    @Autowired
    PolygonService polygonService;

    /**
     * 制图
     *
     * @param json json数据
     * @param dir  父级目录
     * @return 生成的图片路径
     */
    @Override
    public String drawing(String json, String dir) {
        if (StringUtils.isBlank(json)) {
            throw new CommonException(1000, "源数据为空");
        }
        String type = getJsonType(json);
        switch (type) {
            case POINT:
                return pointService.getPointPath(json, dir);
            case LINE:
                return lineService.getLinePath(json, dir);
            case POLYGON:
                return polygonService.getPolygonPath(json, dir);
            default:
                throw new CommonException(1001, "数据类型不支持");
        }
    }

    private String getJsonType(String json) {

        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray features = jsonObject.getJSONArray("features");
        if (features == null || features.size() == 0) {
            return OTHER;
        }
        JSONObject geometry = JSON.parseObject(String.valueOf(features.get(0))).getJSONObject("geometry");
        if (geometry == null) {
            return OTHER;
        }
        String type = geometry.getString("type");
        if (type != null) {
            if (StringUtils.containsIgnoreCase(type, POLYGON)) {
                return POLYGON;
            } else if (StringUtils.containsIgnoreCase(type, LINE)) {
                return LINE;
            } else if (StringUtils.containsIgnoreCase(type, POINT)) {
                return POINT;
            }
        }
        return OTHER;
    }
}
