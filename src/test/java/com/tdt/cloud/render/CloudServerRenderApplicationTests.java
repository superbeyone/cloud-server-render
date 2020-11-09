package com.tdt.cloud.render;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.geotools.geojson.geom.GeometryJSON;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.*;

//@SpringBootTest
class CloudServerRenderApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void readGeoJsonFile() {
        File file = new File("F:\\data\\test\\png\\files\\data2.txt");

        JSONObject resultObj = new JSONObject();
        Map<String, Object> geoMap = new HashMap<>(8);
        geoMap.put("map", "china");
        geoMap.put("show", false);
        resultObj.put("geo", geoMap);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            JSONObject jsonObject = JSON.parseObject(json);
            JSONArray features = jsonObject.getJSONArray("features");
            GeometryJSON geometryJSON = new GeometryJSON();
            List<Map<String, String>> coordsList = new LinkedList<>();
            Double minX = null, minY = null, maxX = null, maxY = null;
            for (Object feature : features) {
                Map<String, String> map = new HashMap<>();
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
                map.put("coords", coordinates);
                coordsList.add(map);
            }

            Double centerX = null, centerY = null;
            try {
                centerX = (maxX + minX) / 2;
                centerY = (maxY + minY) / 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(centerX + "\t" + centerY);
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

        System.out.println(JSON.toJSONString(resultObj));
    }

    private List<Map<String, Object>> getSeriesMap(List<Map<String, String>> coordsList) {
        Map<String, Object> seriesMap = new HashMap<>(8);
        seriesMap.put("type", "lines");
        seriesMap.put("coordinateSystem", "geo");
        seriesMap.put("polyline", true);
        seriesMap.put("data", coordsList);
        seriesMap.put("silent", true);

        List<Map<String, Object>> seriesList = new ArrayList<>();
        seriesList.add(seriesMap);
        return seriesList;
    }

}
