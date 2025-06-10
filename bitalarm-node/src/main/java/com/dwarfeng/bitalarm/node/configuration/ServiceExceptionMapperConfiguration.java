package com.dwarfeng.bitalarm.node.configuration;

import com.dwarfeng.bitalarm.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.bitalarm.stack.exception.AlarmDisabledException;
import com.dwarfeng.bitalarm.stack.exception.ConsumeStoppedException;
import com.dwarfeng.bitalarm.stack.exception.PointNotExistsException;
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
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        destination.put(AlarmDisabledException.class, ServiceExceptionCodes.ALARM_HANDLER_DISABLED);
        destination.put(ConsumeStoppedException.class, ServiceExceptionCodes.CONSUME_HANDLER_STOPPED);
        destination.put(PointNotExistsException.class, ServiceExceptionCodes.POINT_NOT_EXISTS);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINED);
    }
}
