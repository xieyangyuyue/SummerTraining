package com.training.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.pojo.Menu;
import com.training.service.MenuService;
import com.training.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 86187
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2024-08-14 22:29:21
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




