package org.olinonee.framework.knife4j.v3.order.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.olinonee.framework.knife4j.v3.order.entity.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 * <p>模拟测试数据</p>
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-06-29
 */
@Slf4j
@Api(tags = "订单模块")
@RestController
@RequestMapping("/order")
public class OrderController {

	@ApiOperation(value = "所有订单列表", notes = "查询所有订单信息")
    @GetMapping(value = "/list")
    public List<Order> list() {
        return CollectionUtil.newArrayList(new Order(), new Order());
    }

    @GetMapping("/get")
	@ApiOperation(value = "查询订单", notes = "根据 orderNo 查询订单信息")
	@ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "query", dataTypeClass = String.class,
		required = true, example = "TAW100")
    public Order get(@RequestParam("orderNo") String orderNo) {
        log.info("根据用户id查询订单详情，查询的id为 -> {}", orderNo);
        return new Order();
    }

    @PostMapping("add")
	@ApiOperation(value = "添加订单", notes = "添加给定的订单信息")
    public String add(@RequestBody Order order) {
        log.info("添加的订单信息为：{}", JSONUtil.toJsonPrettyStr(order));
        return order.getOrderNo();
    }

    @PostMapping("/update")
	@ApiOperation(value = "更新订单", notes = "根据 orderNo 更新订单信息")
    public Boolean update(@RequestBody Order order) {
        log.info("更新的订单信息为：{}", JSONUtil.toJsonPrettyStr(order));
        return true;
    }

    @DeleteMapping("/delete")
	@ApiOperation(value = "删除订单", notes = "根据 orderNo 删除订单信息")
	@ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "query", dataTypeClass = String.class, required = true, example = "TAW100")
    public Boolean delete(@RequestParam("orderNo") String orderNo) {
        log.info("根据订单id删除指定订单详情，删除的id为 -> {}", orderNo);
        return false;
    }
}
