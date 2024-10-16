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
 * @TableName driver
 */
@TableName(value ="driver")
@Data
public class Driver implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String licensenumber;

    /**
     * 
     */
    private String phonenumber;

    /**
     * 
     */
    private Date licenseexpirydate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}