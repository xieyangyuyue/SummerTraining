package com.training.mapper;

import com.training.pojo.Driver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【driver】的数据库操作Mapper
* @createDate 2024-08-22 16:11:22
* @Entity com.training.pojo.Driver
*/
@Mapper
public interface DriverMapper extends BaseMapper<Driver> {

}




