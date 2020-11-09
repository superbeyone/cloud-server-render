package com.tdt.cloud.render.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tdt.cloud.render.commons.ImgProperties;
import com.tdt.cloud.render.commons.ServerProperties;
import com.tdt.cloud.render.commons.TdtConfig;
import com.tdt.cloud.render.exception.CommonException;
import com.tdt.cloud.render.service.PointService;
import com.tdt.cloud.render.utils.ColorUtil;
import com.tdt.cloud.render.utils.FileUtil;
import com.tdt.cloud.render.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.*;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className PointServiceImpl
 * @description
 * @date 2020-11-04 15:52
 **/

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    TdtConfig tdtConfig;

    /**
     * 返回点数据
     *
     * @param data json数据
     * @param dir  父级目录
     * @return 导出路径
     */
    @Override
    public String getPointPath(String data, String dir) {
        String json = convertJson(data);
        ServerProperties server = tdtConfig.getServer();
        ImgProperties img = tdtConfig.getImg();

        Map<String, Object> params = new HashMap<>(2);
        json = json.replaceAll("\\s+", "").replaceAll("\"", "'");
        params.put("opt", json);
        params.put("width", img.getWidth());
        params.put("height", img.getHeight());
        try {
            String path = FileUtil.getPath(img.getPath(), dir);
            String response = HttpUtil.post(server.getUrl(), params, "utf-8");
            FileUtil.generateImage(response, path);
            return StringUtils.substringAfter(path, img.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(1003, e.getMessage());
        }
    }

    private String convertJson(String data) {

        JSONObject resultObj = new JSONObject();
        Map<String, Object> geoMap = new HashMap<>(8);
        geoMap.put("map", "china");
        geoMap.put("show", false);
        resultObj.put("geo", geoMap);

        try {
            JSONObject jsonObject = JSON.parseObject(data);
            JSONArray features = jsonObject.getJSONArray("features");
            GeometryJSON geometryJSON = new GeometryJSON();
            List<Object> coordsList = new LinkedList<>();
            Double minX = null, minY = null, maxX = null, maxY = null;
            for (Object feature : features) {
                JSONObject featureObj = JSON.parseObject(feature.toString());
                String str = featureObj.getString("geometry");
                Geometry geometry = geometryJSON.read(new StringReader(str));
                Envelope envelope = geometry.getEnvelopeInternal();

                double maxx = envelope.getMaxX();
                double minx = envelope.getMinX();
                maxX = Math.max(maxX == null ? maxx : maxX, maxx);
                minX = Math.min(minX == null ? minx : minX, minx);

                double maxy = envelope.getMaxY();
                double miny = envelope.getMinY();
                maxY = Math.max(maxY == null ? maxy : maxY, maxy);
                minY = Math.min(minY == null ? miny : minY, miny);

                JSONObject geometryObj = JSON.parseObject(str);
                String coordinates = geometryObj.getString("coordinates");
                if (StringUtils.contains(coordinates, "[[")) {
                    coordinates = StringUtils.replace(coordinates, "[[", "[")
                            .replace("]]", "]");
                }
                coordsList.add(JSON.parseArray(coordinates));
            }

            Double centerX = null, centerY = null;
            try {
                centerX = (maxX + minX) / 2;
                centerY = (maxY + minY) / 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (centerX != null && centerY != null) {
                JSONObject geo = resultObj.getJSONObject("geo");
                geo.put("center", new double[]{centerX, centerY});
                List<double[]> bBoxList = new LinkedList<>();
                bBoxList.add(new double[]{minX, maxY});
                bBoxList.add(new double[]{maxX, minY});
                geo.put("boundingCoords", bBoxList);
            }
            resultObj.put("series", getSeriesMap(coordsList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(resultObj);
    }

    private List<Map<String, Object>> getSeriesMap(List<Object> coordsList) {
        Map<String, Object> seriesMap = new HashMap<>(8);
        seriesMap.put("type", "scatter");
        seriesMap.put("coordinateSystem", "geo");
        Map<String, Object> colorMap = new HashMap<>(8);

        colorMap.put("color", ColorUtil.getColor());
        seriesMap.put("itemStyle", colorMap);
        seriesMap.put("data", coordsList);
        seriesMap.put("silent", true);

        List<Map<String, Object>> seriesList = new ArrayList<>();
        seriesList.add(seriesMap);
        return seriesList;
    }
}
