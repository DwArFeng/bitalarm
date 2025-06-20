package com.dwarfeng.bitalarm.impl.configuration;

import com.dwarfeng.bitalarm.sdk.util.ServiceExceptionCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ExceptionCodeOffsetConfiguration {

    @Value("${bitalarm.exception_code_offset}")
    private int exceptionCodeOffset;
    @Value("${bitalarm.exception_code_offset.subgrade}")
    private int subgradeExceptionCodeOffset;
    @Value("${bitalarm.exception_code_offset.snowflake}")
    private int snowflakeExceptionCodeOffset;
    @Value("${bitalarm.exception_code_offset.dwarfeng_datamark}")
    private int dwarfengDatamarkExceptionCodeOffset;

    @PostConstruct
    public void init() {
        ServiceExceptionCodes.setExceptionCodeOffset(exceptionCodeOffset);
        com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.setExceptionCodeOffset(subgradeExceptionCodeOffset);
        com.dwarfeng.sfds.sdk.util.ServiceExceptionCodes.setExceptionCodeOffset(snowflakeExceptionCodeOffset);
        com.dwarfeng.datamark.util.ServiceExceptionCodes.setExceptionCodeOffset(dwarfengDatamarkExceptionCodeOffset);
    }
}
