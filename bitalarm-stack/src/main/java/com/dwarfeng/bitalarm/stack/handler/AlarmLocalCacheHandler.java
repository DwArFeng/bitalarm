package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

import java.util.List;

/**
 * 报警本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmLocalCacheHandler extends LocalCacheHandler<LongIdKey, List<AlarmSetting>> {
}
