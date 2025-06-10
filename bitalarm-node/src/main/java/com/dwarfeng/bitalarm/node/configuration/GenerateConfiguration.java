package com.dwarfeng.bitalarm.node.configuration;

import com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongIdKeyGenerator;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateConfiguration {

    private final GenerateService snowflakeGenerateService;

    public GenerateConfiguration(
            GenerateService snowflakeGenerateService
    ) {
        this.snowflakeGenerateService = snowflakeGenerateService;
    }

    @Bean("snowflakeLongIdKeyGenerator")
    public KeyGenerator<LongIdKey> snowflakeLongIdKeyGenerator() {
        return new SnowflakeLongIdKeyGenerator(snowflakeGenerateService);
    }
}
