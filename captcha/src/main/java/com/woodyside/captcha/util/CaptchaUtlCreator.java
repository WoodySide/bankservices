package com.woodyside.captcha.util;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.noise.StraightLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class CaptchaUtlCreator {

    public static Captcha createCaptcha(Integer width, Integer height) {

        log.info("Generating new captcha with attributes width: {}  and height: {}...", width,height);
        Captcha captcha = new Captcha.Builder(width,height)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new DefaultTextProducer(), new DefaultWordRenderer())
                .addNoise(new StraightLineNoiseProducer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
        log.info("Captcha was generated successfully");
        return captcha;
    }
}
