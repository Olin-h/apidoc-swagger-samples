package org.olinonee.framework.swagger.v3.entity;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Schema(description = "用户实体")
public class User {

	@Schema(description = "编号")
    private Long id;
	@Schema(description = "姓名", required = true)
    private String name;
	@Schema(description = "年龄")
	private Integer age;
	@Schema(description = "工作", required = true)
	private String worker;
	@Schema(description = "单位", required = true)
	private String company;

    public User(Long id, String name, String worker, String company) {
		this.id = id;
        this.name = name;
        this.worker = worker;
        this.company = company;
        this.age=new Random().nextInt(100);
    }
}
