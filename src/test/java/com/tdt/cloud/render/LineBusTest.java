package com.tdt.cloud.render;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className LineBusTest
 * @description
 * @date 2020-11-05 17:59
 **/

public class LineBusTest {

    @Test
    public void testBeiJIngBus() {
        File file = new File("F:\\data\\test\\png\\files\\lines-bus.json");
        File outfile = new File("F:\\data\\test\\png\\files\\lines-bus.geoJson");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outfile))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String bus = builder.toString();
            System.out.println(bus);
            System.out.println();

            JSONArray jsonArray = JSON.parseArray(bus);
//{"type":"FeatureCollection","features":[
            HashMap<String, Object> geoJsonMap = new HashMap<>();
            geoJsonMap.put("type", "FeatureCollection");
            List<Map<String, Object>> featureList = new ArrayList<>(jsonArray.size());
            for (Object o : jsonArray) {
                Map<String, Object> featureMap = new HashMap<>();
                Map<String, Object> geometryMap = new HashMap<>();
                //{"type":"Feature","geometry":{"type":"MultiLineString","coordinates":
                geometryMap.put("type", "LineString");
                geometryMap.put("coordinates", JSON.toJSONString(o));

                featureMap.put("type", "Feature");

                featureMap.put("geometry", geometryMap);
                featureList.add(featureMap);
            }
            geoJsonMap.put("features", featureList);

            System.out.println();

            writer.write(JSON.toJSONString(geoJsonMap));
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
