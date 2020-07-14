package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.Event;
import org.apache.ibatis.annotations.Mapper;

/**
 * 事件DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface EventDao extends BaseMapper<Event> {
}
