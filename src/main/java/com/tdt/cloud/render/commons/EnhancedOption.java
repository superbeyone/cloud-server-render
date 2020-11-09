package com.tdt.cloud.render.commons;

import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.json.OptionUtil;
import com.tdt.cloud.render.config.EchartsConfig;

public class EnhancedOption extends GsonOption implements EchartsConfig {

    private String filepath;

    /**
     * 输出到控制台
     */
    public void print() {
        GsonUtil.print(this);
    }

    /**
     * 输出到控制台
     */
    public void printPretty() {
        GsonUtil.printPretty(this);
    }

    /**
     * 在浏览器中查看
     */
    @Override
    public void view() {
        if (!VIEW) {
            return;
        }
        if (this.filepath != null) {
            try {
                OptionUtil.browse(this.filepath);
            } catch (Exception e) {
                this.filepath = OptionUtil.browse(this);
            }
        } else {
            this.filepath = OptionUtil.browse(this);
        }
    }

    /**
     * 导出到指定文件名
     *
     * @param fileName
     * @return 返回html路径
     */
    @Override
    public String exportToHtml(String fileName) {
        return exportToHtml(EXPORT_PATH, fileName);
    }
}
