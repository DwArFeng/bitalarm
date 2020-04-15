package com.dwarfeng.bitalarm.stack.dao;

import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;

/**
 * 当前报警数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CurrentAlarmDao extends BatchBaseDao<LongIdKey, CurrentAlarm>, EntireLookupDao<CurrentAlarm> {
}
