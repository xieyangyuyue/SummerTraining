package com.training.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.common.QueryPageParam;
import com.training.pojo.Result;
import com.training.pojo.Trainingorder;
import com.training.service.TrainingorderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/trainingorder")
@CrossOrigin
class TrainingorderController {
    @Resource
    private TrainingorderService trainingorderService;

    //表单新增 将表单数据写入数据库
    @PostMapping("/save")
    public Result save(@RequestBody Trainingorder trainingorder) {
        return trainingorderService.save(trainingorder) ? Result.success() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Trainingorder trainingorder) {
        return trainingorderService.updateById(trainingorder) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return trainingorderService.removeById(id) ? Result.success() : Result.fail();
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody Trainingorder trainingorder) {
        return trainingorderService.saveOrUpdate(trainingorder) ? Result.success() : Result.fail();
    }

    //根据name查询是否有重复用户名
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String name) {
        List list = trainingorderService.lambdaQuery().eq(Trainingorder::getName,name).list();
        return !list.isEmpty() ? Result.success(list) : Result.fail();
    }
 
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam) {
        //打印其他信息
        HashMap param = queryPageParam.getParam();
        //从前端传来的param的  name  sex  进行 lambdaQueryWrapper
        String name = (String) param.get("name");
        String priority = (String) param.get("priority");
        String trainingtype = (String) param.get("trainingtype");
        String deliver = (String) param.get("deliver");
        String remark = (String) param.get("remark");





        /**
         * setCurrent 设置 页数
         * setSize 设置条数
         */
        Page<Trainingorder> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        //按照名字查询匹配
        LambdaQueryWrapper<Trainingorder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 判断是否为空
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Trainingorder::getName,name);
        }
        if (StringUtils.isNotBlank(priority) && !"null".equals(priority)) {
            lambdaQueryWrapper.like(Trainingorder::getPriority,priority);
        }
        if (StringUtils.isNotBlank(trainingtype) && !"null".equals(trainingtype)) {
            lambdaQueryWrapper.like(Trainingorder::getTrainingtype,trainingtype);
        }
        if (StringUtils.isNotBlank(deliver) && !"null".equals(deliver)) {
            lambdaQueryWrapper.like(Trainingorder::getWeight,deliver);
        }
        if (StringUtils.isNotBlank(remark) && !"null".equals(remark)) {
            lambdaQueryWrapper.like(Trainingorder::getRemark,remark);
        }
        //封装查询结果
        IPage<Trainingorder> result = trainingorderService.page(page, lambdaQueryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }
}
