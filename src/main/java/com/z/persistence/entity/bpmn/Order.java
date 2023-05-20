package com.z.persistence.entity.bpmn;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Order
 *
 * @author Lee
 * @date 2023/3/5
 */
@Data
@Accessors
public class Order {


    /**
     * 订单原价金额
     */
    private int price;

    /**
     *下单人
     */
    private User user;

    /**
     *积分
     */
    private int score;

    /**
     * 下单日期
     */
    private Date bookingDate;

    /**
     * 数量
     */
    private Integer amount;
}
