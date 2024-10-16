package com.training.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.pojo.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86187
* @description 针对表【record】的数据库操作Service
* @createDate 2024-08-16 21:57:01
*/
public interface RecordService extends IService<Record> {

    IPage listPageSelf(IPage<Record> page, Wrapper Wrapper);
}
