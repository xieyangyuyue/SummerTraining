package com.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.mapper.RecordMapper;
import com.training.pojo.Record;
import com.training.service.RecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 86187
 * @description 针对表【record】的数据库操作Service实现
 * @createDate 2024-08-16 21:57:01
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
        implements RecordService {
    @Resource
    private RecordMapper recordMapper;
    @Override
    public IPage listPageSelf(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.listPageSelf(page, wrapper);
    }

}



