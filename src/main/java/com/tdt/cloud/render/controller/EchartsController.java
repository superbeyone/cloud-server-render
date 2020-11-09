package com.tdt.cloud.render.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.json.GsonOption;
import com.tdt.cloud.render.commons.JsonResult;
import com.tdt.cloud.render.entities.BarData;
import com.tdt.cloud.render.entities.EchartBar;
import com.tdt.cloud.render.utils.EchartsUtil;
import com.tdt.cloud.render.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Api(value = "生成Echarts图表API接口", tags = "Echarts图表")
@RequestMapping("/echarts")
@RestController
public class EchartsController {

    private final static Logger logger = LoggerFactory.getLogger(EchartsController.class);

    @Value("${img-url}")
    private String imgUrl;

    @Value("${img-url-path}")
    private String imgUrlPath;

    @Value("${request-url}")
    private String requestUrl;


    private String readFile() {
        StringBuilder builder = new StringBuilder();
        File file = new File("F:\\data\\test\\png\\files\\template.json");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @ApiOperation(value = "生成柱状图")
    @RequestMapping(value = "/bar", method = RequestMethod.POST)
    public JsonResult createBar(@RequestBody BarData barData) {

        GsonOption option = EchartBar.createBar(barData);

        String optionStr = JSONObject.toJSONString(option);
        optionStr = readFile();
        if (optionStr == null || "".equals(optionStr)) {
            return JsonResult.fail(-1, "Fail");
        }
        logger.info("bar-optionStr = " + optionStr);

        File oldfile = null;
        File newfile = null;
        String oldFilePath = null;
        String newFilePath = null;

        try {
            // 根据option参数发起请求，转换为base64
            String base64 = EchartsUtil.generateEchartsBase64(optionStr, requestUrl);

            long nowStr = Calendar.getInstance().getTimeInMillis();
            //图片名
//            String imageName = "bar" + nowStr + RandomUtils.getRandomString(4) + ".png";
            String imageName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
            logger.info("bar图片：" + imageName);

            oldfile = FileUtil.generateImage(base64, imgUrl + imageName);
//            newfile = new File(imgUrl + "new" + imageName);
//            oldFilePath = imgUrl + imageName;
//            newFilePath = imgUrl + "new" + imageName;
//
//            logger.info("file = " + oldfile);
//
//            logger.info("oldFilePath = " + oldFilePath);
//            logger.info("newFilePath = " + newFilePath);
//
//            String logoText = "superbeyone";


            //添加水印
//            ImageUtil.markImageByText("logoText", oldFilePath, newFilePath, -15, new Color(190, 190, 190), "png");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发生了异常，" + e.getMessage());
            return  JsonResult.fail(-1, "Fail");
        }
        return new JsonResult(1, newFilePath, "SUCCESS");
    }
}