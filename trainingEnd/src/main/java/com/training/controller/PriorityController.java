package com.training.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.common.QueryPageParam;
import com.training.pojo.Priority;
import com.training.pojo.Result;
import com.training.service.PriorityService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/priority")
@CrossOrigin
class PriorityController {
    @Resource
    private PriorityService priorityService;

    //表单新增 将表单数据写入数据库
    @PostMapping("/save")
    public Result save(@RequestBody Priority priority) {
        return priorityService.save(priority) ? Result.success() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Priority priority) {
        return priorityService.updateById(priority) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return priorityService.removeById(id) ? Result.success() : Result.fail();
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody Priority priority) {
        return priorityService.saveOrUpdate(priority) ? Result.success() : Result.fail();
    }

    //根据name查询是否有重复用户名
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String name) {
        List list = priorityService.lambdaQuery().eq(Priority::getName, name).list();
        return !list.isEmpty() ? Result.success(list) : Result.fail();
    }

    @GetMapping("/list")
    public Result list() {
        List list = priorityService.list();
        return Result.success(list);
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam) {
        //打印其他信息
        HashMap param = queryPageParam.getParam();
        //从前端传来的param的  name  sex  进行 lambdaQueryWrapper
        String name = (String) param.get("name");
        String remark = (String) param.get("remark");


        /**
         * setCurrent 设置 页数
         * setSize 设置条数
         */
        Page<Priority> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        //按照名字查询匹配
        LambdaQueryWrapper<Priority> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 判断是否为空
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Priority::getName, name);
        }
        if (StringUtils.isNotBlank(remark) && !"null".equals(remark)) {
            lambdaQueryWrapper.like(Priority::getRemark, remark);
        }
        //封装查询结果
        IPage<Priority> result = priorityService.page(page, lambdaQueryWrapper);
        return Result.success(result.getRecords(), result.getTotal());

    }

}
