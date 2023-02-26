package com.z.persistence.mysql.p2.dao;

import com.z.persistence.entity.p2.Tuser2;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Tuser2Mapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Tuser2 record);

    int insertSelective(Tuser2 record);

    Tuser2 selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Tuser2 record);

    int updateByPrimaryKey(Tuser2 record);
}