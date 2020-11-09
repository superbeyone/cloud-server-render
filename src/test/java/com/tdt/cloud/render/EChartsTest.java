package com.tdt.cloud.render;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.DataView;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.feature.Mark;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Pie;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className EChartsTest
 * @description
 * @date 2020-10-28 15:38
 **/

public class EChartsTest {

    @Test
    public void testGenerateImgByECharts() {

    }

    private String readGeoJson() {
        File file = new File("");

        return "";
    }


    /**
     * 饼图
     */
    @Test
    public void testPie() {
        String[] types = {"邮件营销", "联盟广告", "视频广告"};
        int[] datas = {120, 132, 101};
        String title = "广告数据";
        GsonOption option = new GsonOption();

        option.title().text(title).subtext("虚构").x("center");// 大标题、小标题、标题位置

        // 提示工具 鼠标在每一个数据项上，触发显示提示数据
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");

        // 工具栏
        option.toolbox().show(true).feature(new Mark().show(true),// 辅助线
                new DataView().show(true).readOnly(false),// 数据视图
                new MagicType().show(true).type(new Magic[]{Magic.pie, Magic.funnel}), //饼图、漏斗图切换
                new Option().series(new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548)),// 漏斗图设置
                Tool.restore,// 还原
                Tool.saveAsImage);// 保存为图片

        option.legend().orient(Orient.horizontal).x("left").data(types);// 图例及位置

        option.calculable(true);// 拖动进行计算

        Pie pie = new Pie();

        // 标题、半径、位置
        pie.name(title).radius("55%").center("50%", "60%");

        // 循环数据
        for (int i = 0; i < types.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("value", datas[i]);
            map.put("name", types[i]);
            pie.data(map);
        }

        option.series(pie);

        System.out.println(option.toPrettyString());
//        option.view();
//        option.exportToHtml("D:\\test\\img", System.currentTimeMillis() + ".html");
    }


}
