package com.dwarfeng.bitalarm.node.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.bitalarm.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        //实体对象。
        ParserConfig.getGlobalInstance().addAccept(FastJsonAlarmSetting.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonAlarmHistory.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonCurrentAlarm.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonAlarmInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonAlarmTypeIndicator.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonPoint.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
