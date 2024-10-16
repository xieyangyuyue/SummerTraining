package com.training.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName trainingorder
 */
@TableName(value ="trainingorder")
@Data
public class Trainingorder implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 货名
     */
    private String name;

    /**
     * 公司
     */
    private Integer priority;

    /**
     * 运输方式
     */
    private Integer trainingtype;

    /**
     * 数量
     */
    private Integer weight;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}