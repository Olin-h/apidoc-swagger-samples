package org.olinonee.framework.swagger.v3.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.olinonee.framework.swagger.v3.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * <p>模拟测试数据</p>
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-06-29
 */
@Slf4j
@Tag(name = "用户模块", description = "用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {

	@Operation(method = "GET", summary = "所有用户列表", description = "查询所有用户信息")
    @GetMapping(value = "/list")
    public List<User> list() {
        return CollectionUtil.newArrayList(new User(-1L,"user1", "Java开发工程师", "公司2")
                , new User(2L,"user2", "C开发工程师", "公司1")
                , new User(3L,"user3", "JavaScript工程师", "公司3")
                , new User(4L,"user4", "Ui工程师", "公司4")
                , new User(5L,"user5", "总经理", "公司1"));
    }

    @GetMapping("/get")
	@Operation(method = "GET", summary = "查询用户", description = "根据 id 查询用户信息")
	@Parameter(name = "主键id", description = "主键id", in = ParameterIn.QUERY, required = true, example = "1024")
    public User get(@RequestParam("id") Long id) {
        log.info("根据用户id查询用户详情，查询的id为 -> {}", id);
        return new User(5L,"user5", "总经理", "公司1");
    }

    @PostMapping("add")
	@Operation(method = "POST", summary = "添加用户", description = "添加给定的用户信息")
    public Long add(@RequestBody User user) {
        log.info("添加的用户信息为：{}", JSONUtil.toJsonPrettyStr(user));
        return user.getId();
    }

    @PostMapping("/update")
	@Operation(method = "POST", summary = "更新用户", description = "根据 id 更新用户信息")
    public Boolean update(@RequestBody User user) {
        log.info("更新的用户信息为：{}", JSONUtil.toJsonPrettyStr(user));
        return true;
    }

    @DeleteMapping("/delete")
	@Operation(method = "DELETE", summary = "删除用户", description = "根据 id 删除用户信息")
	@Parameter(name = "主键id", description = "主键id", in = ParameterIn.QUERY, required = true, example = "1024")
    public Boolean delete(@RequestParam("id") Long id) {
        log.info("根据用户id删除指定用户详情，删除的id为 -> {}", id);
        return false;
    }
}
