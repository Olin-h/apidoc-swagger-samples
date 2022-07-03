package org.olinonee.framework.swagger.v2.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.olinonee.framework.swagger.v2.entity.User;
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
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "查询用户列表", notes = "目前仅仅是作为测试，所以返回用户全列表")
    @GetMapping(value = "/list")
    public List<User> list() {
        return CollectionUtil.newArrayList(new User("user1", "Java开发工程师", "公司2")
                , new User("user2", "C开发工程师", "公司1")
                , new User("user3", "JavaScript工程师", "公司3")
                , new User("user4", "Ui工程师", "公司4")
                , new User("user5", "总经理", "公司1"));
    }

    @GetMapping("/get")
    @ApiOperation("获得指定用户编号的用户")
    @ApiImplicitParam(name = "id", value = "用户编号",
		paramType = "query", dataTypeClass = Long.class,
		required = true, example = "1024")
    public User get(@RequestParam("id") Long id) {
        log.info("根据用户id查询用户详情，查询的id为 -> {}", id);
        return new User("user5", "总经理", "公司1");
    }

    @PostMapping("add")
    @ApiOperation("添加用户")
    public Integer add(User user) {
        log.info("添加的用户信息为：{}", JSONUtil.toJsonPrettyStr(user));
        // 插入用户记录，返回编号
        return UUID.fastUUID().hashCode();
    }

    @PostMapping("/update")
    @ApiOperation("更新指定用户编号的用户")
    public Boolean update(User user) {
        log.info("更新的用户信息为：{}", JSONUtil.toJsonPrettyStr(user));
        // 返回更新是否成功
        return true;
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除指定用户编号的用户")
    @ApiImplicitParam(name = "id", value = "用户编号",
		paramType = "query", dataTypeClass = Long.class,
		required = true, example = "1024")
    public Boolean delete(@RequestParam("id") Long id) {
        log.info("根据用户id删除指定用户详情，删除的id为 -> {}", id);
        // 删除用户记录
        return false;
    }
}
