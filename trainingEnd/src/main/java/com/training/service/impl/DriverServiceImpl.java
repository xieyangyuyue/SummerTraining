package com.training.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.pojo.Driver;
import com.training.service.DriverService;
import com.training.mapper.DriverMapper;
import org.springframework.stereotype.Service;

/**
* @author 86187
* @description 针对表【driver】的数据库操作Service实现
* @createDate 2024-08-22 16:11:22
*/
@Service
public class DriverServiceImpl extends ServiceImpl<DriverMapper, Driver>
    implements DriverService{

}




