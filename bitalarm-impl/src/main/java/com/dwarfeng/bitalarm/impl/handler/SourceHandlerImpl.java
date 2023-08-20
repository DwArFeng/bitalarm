package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.handler.Source;
import com.dwarfeng.bitalarm.stack.handler.SourceHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class SourceHandlerImpl implements SourceHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceHandlerImpl.class);

    private final AlarmProcessor alarmProcessor;

    private final List<Source> sources;

    private final InternalSourceContext sourceContext = new InternalSourceContext();

    public SourceHandlerImpl(AlarmProcessor alarmProcessor, List<Source> sources) {
        this.alarmProcessor = alarmProcessor;
        this.sources = Optional.ofNullable(sources).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() {
        LOGGER.info("初始化数据源...");
        sources.forEach(source -> source.init(sourceContext));
    }

    @Override
    public List<Source> all() {
        return sources;
    }

    private class InternalSourceContext implements Source.Context {

        @Override
        public void processAlarm(LongIdKey pointKey, byte[] data, Date happenedDate) throws HandlerException {
            alarmProcessor.processAlarm(pointKey, data, happenedDate);
        }
    }
}
