package com.training.mapper;

import com.training.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2024-08-14 22:29:21
* @Entity com.training.pojo.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}




