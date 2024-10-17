package com.training.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.common.QueryPageParam;
import com.training.pojo.*;
import com.training.pojo.Record;
import com.training.service.DeliveryService;
import com.training.service.DriverService;
import com.training.service.RecordService;
import com.training.service.TrainingorderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping("/record")
@CrossOrigin
class RecordController {
    @Resource
    private RecordService recordService;
    @Resource
    private TrainingorderService trainingorderService;
    @Resource
    private DeliveryService deliveryService;
    @Resource
    private DriverService driverService;

    @PostMapping("/listPageSelf")

    public Result listPageSelf(@RequestBody QueryPageParam queryPageParam) {

        //打印其他信息
        HashMap param = queryPageParam.getParam();
        String name = (String) param.get("name");
        String priority = (String) param.get("priority");
        String trainingtype = (String) param.get("trainingtype");
        String roleId = (String) param.get("roleId");
        String userid = (String) param.get("userid");



        Page<Record> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        //按照名字查询匹配
        QueryWrapper<Record> QueryWrapper = new QueryWrapper<>();

//        QueryWrapper.apply(" record.goods = goods.id and goods.storage = storage.id and goods.goodsType = goodstype.id ");
        QueryWrapper.apply(" record.goods = trainingorder.id and trainingorder.priority = priority.id and trainingorder.trainingtype = trainingtype.id ");
        if("2".equals(roleId)){
//            QueryWrapper.eq(Record::getUserid(),userid);
            QueryWrapper.apply("record.userId="+userid);
        }
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            QueryWrapper.like("trainingorder.name", name);
        }
//        if (StringUtils.isNotBlank(trainingtype) && !"null".equals(trainingtype)) {
//            QueryWrapper.like("priority.id", trainingtype);
//        }
//        if (StringUtils.isNotBlank(priority) && !"null".equals(priority)) {
//            QueryWrapper.like("trainingtype.id", priority);
//        }
        if (StringUtils.isNotBlank(trainingtype) && !"null".equals(trainingtype)) {
            QueryWrapper.like("trainingtype.id", trainingtype);
        }
        if (StringUtils.isNotBlank(priority) && !"null".equals(priority)) {
            QueryWrapper.like("priority.id", priority);
        }
        //封装查询结果
        IPage result = recordService.listPageSelf(page, QueryWrapper);
//            IPage<Record> result = recordService.listPageSelf(page, lambdaQueryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody RecordRes record) {
        record.setCreatetime(LocalDateTime.now());
        Trainingorder trainingorder =trainingorderService.getById(record.getGoods());
        trainingorder.setWeight(1);
        //修改订单状态
        trainingorderService.updateById(trainingorder);
        //配送管理
        Delivery delivery= new Delivery();
        delivery.setRecipientname(record.getUsername());
//        System.out.println(record.getUsername());
        delivery.setDeliverydate(LocalDateTime.now());
        // 创建Random对象实例
        Random random = new Random();
        // 生成一个0到3之间的随机整数  地址
        int randomNumber = random.nextInt(3)+1;
        delivery.setAddress("address"+randomNumber);
        //司机
        int randomNumber1 = random.nextInt(5)+1;
        Driver driver=driverService.getById(randomNumber1);
        delivery.setDriver(driver.getName());
        //状态
//        int randomNumber2 = random.nextInt(2);
        delivery.setStatus(0);


        deliveryService.save(delivery);
        return recordService.save(record) ? Result.success() : Result.fail();
    }
}