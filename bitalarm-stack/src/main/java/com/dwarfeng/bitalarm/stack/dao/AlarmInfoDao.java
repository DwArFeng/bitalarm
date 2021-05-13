package com.dwarfeng.bitalarm.stack.dao;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 报警信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmInfoDao extends BatchBaseDao<LongIdKey, AlarmInfo>, EntireLookupDao<AlarmInfo>,
        PresetLookupDao<AlarmInfo> {
}
