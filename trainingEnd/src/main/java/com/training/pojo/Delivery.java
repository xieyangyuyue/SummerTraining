package com.training.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName delivery
 */
@TableName(value ="delivery")
@Data
public class Delivery implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String recipientname;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private LocalDateTime deliverydate;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private String driver;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}