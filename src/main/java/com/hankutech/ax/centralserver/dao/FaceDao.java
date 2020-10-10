package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.Face;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FaceDao extends BaseMapper<Face> {
}