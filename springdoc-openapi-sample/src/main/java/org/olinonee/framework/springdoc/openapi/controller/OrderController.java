package org.olinonee.framework.springdoc.openapi.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.olinonee.framework.springdoc.openapi.entity.Order;
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
@Tag(name = "订单模块", description = "订单控制器")
@RestController
@RequestMapping("/order/user")
public class OrderController {

	@Operation(method = "GET", summary = "所有订单列表", description = "查询所有订单信息")
    @GetMapping(value = "/list")
    public List<Order> list() {
        return CollectionUtil.newArrayList(new Order(), new Order());
    }

    @GetMapping("/get")
	@Operation(method = "GET", summary = "查询订单", description = "根据 orderNo 查询订单信息")
	@Parameter(name = "orderNo", description = "订单编号", in = ParameterIn.QUERY, required = true, example = "TAW100")
    public Order get(@RequestParam("orderNo") String orderNo) {
        log.info("根据用户id查询订单详情，查询的id为 -> {}", orderNo);
        return new Order();
    }

    @PostMapping("add")
	@Operation(method = "POST", summary = "添加订单", description = "添加给定的订单信息")
    public String add(@RequestBody Order order) {
        log.info("添加的订单信息为：{}", JSONUtil.toJsonPrettyStr(order));
        return order.getOrderNo();
    }

    @PostMapping("/update")
	@Operation(method = "POST", summary = "更新订单", description = "根据 orderNo 更新订单信息")
    public Boolean update(@RequestBody Order order) {
        log.info("更新的订单信息为：{}", JSONUtil.toJsonPrettyStr(order));
        return true;
    }

    @DeleteMapping("/delete")
	@Operation(method = "DELETE", summary = "删除订单", description = "根据 orderNo 删除订单信息")
	@Parameter(name = "orderNo", description = "订单编号", in = ParameterIn.QUERY, required = true, example = "TAW100")
    public Boolean delete(@RequestParam("orderNo") String orderNo) {
        log.info("根据订单id删除指定订单详情，删除的id为 -> {}", orderNo);
        return false;
    }
}
