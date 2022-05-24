package com.dwarfeng.bitalarm.stack.dao;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;

/**
 * 报警类型指示器数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface AlarmTypeIndicatorDao extends BatchBaseDao<StringIdKey, AlarmTypeIndicator>,
        EntireLookupDao<AlarmTypeIndicator> {
}
