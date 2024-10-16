package com.training.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 菜单编码
     */
    private String menucode;

    /**
     * 菜单名字
     */
    private String menuname;

    /**
     * 菜单级别
     */
    private String menulevel;

    /**
     * 菜单的父code
     */
    private String menuparentcode;

    /**
     * 点击触发的函数
     */
    private String menuclick;

    /**
     * 权限 0超级管理员，1管理员，2普通用户 可以组合使用
     */
    private String menuright;

    /**
     * 路径
     */
    private String menucomponent;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}