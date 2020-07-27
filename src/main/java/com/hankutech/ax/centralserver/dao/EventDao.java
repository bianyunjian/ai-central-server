package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.Event;
import com.hankutech.ax.centralserver.pojo.query.HistoryEventParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.vo.event.history.HistoryEventVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 事件DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface EventDao extends BaseMapper<Event> {

    List<HistoryEventVO> getTableList(@Param("page")PagedParams pagedParams, @Param("query")HistoryEventParams queryParams);

    long getTableTotal(@Param("query")HistoryEventParams queryParams);



}
