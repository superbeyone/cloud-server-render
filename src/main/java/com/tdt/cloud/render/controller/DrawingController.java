package com.tdt.cloud.render.controller;

import com.tdt.cloud.render.commons.JsonResult;
import com.tdt.cloud.render.service.DrawingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className DrawingController
 * @description
 * @date 2020-11-03 15:46
 **/

@RestController
@RequestMapping("/draw")
public class DrawingController {

    @Autowired
    DrawingService drawingService;

    @PostMapping
    public JsonResult<String> drawingPic(String data, String dir) {
        String path = drawingService.drawing(data, dir);
        return JsonResult.getInstance().success(path);
    }

    @PostMapping("/draw2")
    public JsonResult<String> drawingPic(@RequestBody String data) {
        String dir = "aabbcc";
        String path = drawingService.drawing(data, dir);
        return JsonResult.getInstance().success(path);
    }
}
