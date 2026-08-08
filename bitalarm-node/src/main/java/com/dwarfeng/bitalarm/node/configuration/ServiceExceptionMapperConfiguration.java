package com.dwarfeng.bitalarm.node.configuration;

import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> des = ServiceExceptionHelper.putDefaultDestination(null);
        des = com.dwarfeng.springtelqos.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.springterminator.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.datamark.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.dcti.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.bitalarm.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        return new MapServiceExceptionMapper(des, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINED);
    }
}
