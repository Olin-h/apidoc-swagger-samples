package org.olinonee.framework.swagger.service.user.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Random;

/**
 * 用户实体
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-06-29
 */
@ApiModel(value = "用户实体")
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class User {

    @ApiModelProperty(value = "编号")
    private Long id;
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "工作")
    private String worker;

    @ApiModelProperty(value = "单位")
    private String company;

    public User(String name, String worker, String company) {
        this.id = new Random().nextLong();
        this.name = name;
        this.worker = worker;
        this.company = company;
        this.age=new Random().nextInt(100);
    }
}
