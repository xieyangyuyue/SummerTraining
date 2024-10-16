package com.training.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName priority
 */
@TableName(value ="priority")
@Data
public class Priority implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 运输等级
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     */
    private Date createtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}