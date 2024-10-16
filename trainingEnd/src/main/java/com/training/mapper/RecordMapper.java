package com.training.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.training.pojo.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 86187
* @description 针对表【record】的数据库操作Mapper
* @createDate 2024-08-16 21:57:01
* @Entity com.training.pojo.Record
*/
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    IPage<Record> listPageSelf(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




