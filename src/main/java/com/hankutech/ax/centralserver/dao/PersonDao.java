package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.model.Person;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人员DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface PersonDao extends BaseMapper<Person> {
}
