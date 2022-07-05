package org.olinonee.framework.knife4j.order.entity;


import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单实体
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-06-29
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "订单实体")
public class Order {

	@ApiModelProperty(value = "订单编号")
    private String orderNo;
	@ApiModelProperty(value = "订单日期")
	private Date orderTime;
	@ApiModelProperty(value = "订单名称")
	private String name;

	public Order() {
		DecimalFormat df = new DecimalFormat("00000");
		this.orderNo = "TAW" + df.format(new Random().nextInt(1000));
		this.name = "订单" + new Random().nextInt(1000);
		this.orderTime = DateUtil.date();
	}
}
