package com.training.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.common.QueryPageParam;
import com.training.pojo.Delivery;
import com.training.pojo.Result;
import com.training.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/delivery")
@CrossOrigin
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;

	@GetMapping("/list")
	public Result list() {
		List<Delivery> deliveries = deliveryService.list();
		return Result.success(deliveries);
	}

	@PostMapping("/save")
	public Result save(@RequestBody Delivery delivery) {
		return deliveryService.save(delivery) ? Result.success() : Result.fail();
	}

	@PostMapping("/update")
	public Result update(@RequestBody Delivery delivery) {
		return deliveryService.updateById(delivery) ? Result.success() : Result.fail();
	}

	@GetMapping("/delete")
	public Result delete(@RequestParam Integer id) {
		return deliveryService.removeById(id) ? Result.success() : Result.fail();
	}

	@PostMapping("/listPage")
	public Result listPage(@RequestBody QueryPageParam queryPageParam) {
		//打印其他信息
		HashMap param = queryPageParam.getParam();
		//从前端传来的param的  name  sex  进行 lambdaQueryWrapper
		String name = (String) param.get("name");


		/**
		 * setCurrent 设置 页数
		 * setSize 设置条数
		 */
		Page<Delivery> page = new Page<>();
		page.setCurrent(queryPageParam.getPageNum());
		page.setSize(queryPageParam.getPageSize());

		//按照名字查询匹配
		LambdaQueryWrapper<Delivery> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		// 判断是否为空
		if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
			lambdaQueryWrapper.like(Delivery::getRecipientname, name);
		}
//        封装查询结果
		IPage<Delivery> result = deliveryService.page(page, lambdaQueryWrapper);
		return Result.success(result.getRecords(), result.getTotal());
	}
}
