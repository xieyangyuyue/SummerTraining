package com.training.controller;

import com.training.pojo.Result;
import com.training.service.LevelService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/level")
@CrossOrigin
public class LevelController {
@Resource
private LevelService levelService;


    @GetMapping("/list")
    public Result list() {
        List list = levelService.list();
        return Result.success(list);
    }
}
