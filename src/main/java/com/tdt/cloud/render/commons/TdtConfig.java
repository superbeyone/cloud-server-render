package com.tdt.cloud.render.commons;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.superbeyone
 * @project cloud-server-render
 * @className TdtConfig
 * @description
 * @date 2020-11-03 15:17
 **/

@Data
@Configuration
@ConfigurationProperties("tdt")
public class TdtConfig {

    private ServerProperties server;

    private ImgProperties img;

}
