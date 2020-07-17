package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.Person;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface PersonDao extends BaseMapper<Person> {


    List<PersonVO> getListByDeviceId(@Param("deviceId") Integer deviceId);




}
