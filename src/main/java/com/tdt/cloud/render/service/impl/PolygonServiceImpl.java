package com.tdt.cloud.render.service.impl;

import com.alibaba.fastjson.JSON;
import com.tdt.cloud.render.commons.ImgProperties;
import com.tdt.cloud.render.commons.TdtConfig;
import com.tdt.cloud.render.exception.CommonException;
import com.tdt.cloud.render.service.PolygonService;
import com.tdt.cloud.render.utils.ColorUtil;
import com.tdt.cloud.render.utils.FileUtil;
import com.tdt.cloud.render.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className PolygonServiceImpl
 * @description
 * @date 2020-11-03 15:14
 **/

@Service
public class PolygonServiceImpl implements PolygonService {

    @Autowired
    TdtConfig tdtConfig;

    private static final String REGISTER_MAP_KEY = "AREA";

    private static final String REGISTER_MAP = "registerMap";

    /**
     * 返回面数据
     *
     * @param data json数据
     * @param dir  父级目录
     * @return 导出路径
     */
    @Override
    public String getPolygonPath(String data, String dir) {

        String url = tdtConfig.getServer().getUrl();
        ImgProperties img = tdtConfig.getImg();
        Map<String, Object> params = new HashMap<>(4);
        String series = getSeries();
        series = series.replaceAll("\\s+", "").replaceAll("\"", "'");
        params.put("opt", series);
        params.put("width", img.getWidth());
        params.put("height", img.getHeight());

        Map<String, Object> registerMap = new HashMap<>(2);
        registerMap.put("key", REGISTER_MAP_KEY);
        registerMap.put("value", data);
        params.put(REGISTER_MAP, JSON.toJSONString(registerMap));


        try {
            String response = HttpUtil.post(url, params, "utf-8");
            String path = FileUtil.getPath(img.getPath(), dir);
            FileUtil.generateImage(response, path);
            return StringUtils.substringAfter(path, img.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(1003, e.getMessage());
        }
    }

    private String getSeries() {

        String color = ColorUtil.getColor();
        Map<String, Object> seriesMap = new HashMap<>(8);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("type", "map");
        map.put("map", REGISTER_MAP_KEY);
        HashMap<String, Object> itemStyleMap = new HashMap<>();
        itemStyleMap.put("areaColor", color);
        itemStyleMap.put("borderColor", "#FFF");
        itemStyleMap.put("borderWidth", 1);
        itemStyleMap.put("shadowOffsetX", 3);
        itemStyleMap.put("shadowOffsetY", 3);
        itemStyleMap.put("shadowColor", "rgba(0,0,0,0.5)");

        map.put("itemStyle", itemStyleMap);
        maps.add(map);
        seriesMap.put("series", maps);
        return JSON.toJSONString(seriesMap);
    }

}
