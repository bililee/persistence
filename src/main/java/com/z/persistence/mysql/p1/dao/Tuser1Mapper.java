package com.z.persistence.mysql.p1.dao;

import com.z.persistence.entity.p1.Tuser1;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Tuser1Mapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Tuser1 record);

    int insertSelective(Tuser1 record);

    Tuser1 selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Tuser1 record);

    int updateByPrimaryKey(Tuser1 record);
}