package com.training.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.common.QueryPageParam;
import com.training.pojo.Driver;
import com.training.pojo.Result;
import com.training.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/driver")
@CrossOrigin
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/list")
    public Result list() {
        List<Driver> drivers = driverService.list();
        return Result.success(drivers);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Driver driver) {
        return driverService.save(driver) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Driver driver) {
        return driverService.updateById(driver) ? Result.success() : Result.fail();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return driverService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/random")
    public Result getRandomDriver() {
        // 获取所有司机
        List<Driver> drivers = driverService.list();

        if (drivers.isEmpty()) {
            return Result.fail();
        }

        // 随机选择一个司机
        Random random = new Random();
        Driver randomDriver = drivers.get(random.nextInt(drivers.size()));

        return Result.success(randomDriver);
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam) {
        HashMap param = queryPageParam.getParam();
        String name = (String) param.get("name");
        String license = (String) param.get("license");

        Page<Driver> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<Driver> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.like(Driver::getName, name);
        }
        if (StringUtils.isNotBlank(license)) {
            lambdaQueryWrapper.like(Driver::getLicensenumber, license);
        }

        IPage<Driver> result = driverService.page(page, lambdaQueryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }
}