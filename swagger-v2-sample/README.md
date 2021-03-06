## swagger v2 文档说明
### quickstart
启动`swagger-v2-sample`模块的应用程序

浏览器访问如下地址：
```http request
http://127.0.0.1:600/swagger-v2/swagger-ui.html
```
---
### 引入的依赖
```xml
<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>

    <springfox-swagger.version>2.9.2</springfox-swagger.version>
    <swagger.models.version>1.6.6</swagger.models.version>
</properties>

<dependencies>
    <!-- 引入 Swagger 依赖 -->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>${springfox-swagger.version}</version>
        <exclusions>
            <!-- 排除依赖 1.5.20版本，避免提示 java.lang.NumberFormatException: For input string: ""警告 -->
            <exclusion>
                <groupId>io.swagger</groupId>
                <artifactId>swagger-annotations</artifactId>
            </exclusion>
            <exclusion>
                <groupId>io.swagger</groupId>
                <artifactId>swagger-models</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <!-- 解决进入swagger页面报类型转换错误，排除1.5.20版本的引用，手动增加1.6.6版本 -->
    <dependency>
        <groupId>io.swagger</groupId>
        <artifactId>swagger-annotations</artifactId>
        <version>${swagger.models.version}</version>
    </dependency>
    <dependency>
        <groupId>io.swagger</groupId>
        <artifactId>swagger-models</artifactId>
        <version>${swagger.models.version}</version>
    </dependency>

    <!-- 引入 Swagger UI 依赖，以实现 API 接口的 UI 界面 -->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>${springfox-swagger.version}</version>
    </dependency>
</dependencies>
```
---
### 添加配置
```yaml
server:
  port: 600
  servlet:
    context-path: /swagger-v2
  
spring:
  application:
    name: swagger-v2-sample
  mvc:
    # 解决Spring Boot 2.6.X swagger接口列表不显示的问题 这是因为Springfox使用的路径匹配是基于AntPathMatcher的，而Spring Boot 2.6.X使用的是PathPatternMatcher。
    pathmatch:
      matching-strategy: ant_path_matcher
```
---
### swagger2 注解
```text
@Api：用在请求的类上，表示对类的说明
    tags="说明该类的作用，可以在UI界面上看到的注解"
    value="该参数没什么意义，在UI界面上也看到，所以不需要配置"

@ApiOperation：用在请求的方法上，说明方法的用途、作用
    value="说明方法的用途、作用"
    notes="方法的备注说明"

@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · div（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值

@ApiResponses：用在请求的方法上，表示一组响应
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类

@ApiModel：用于响应类上，表示一个返回响应数据的信息
            （这种一般用在post创建的时候，使用@RequestBody这样的场景，
            请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    @ApiModelProperty：用在属性上，描述响应类的属性

```

---
### 注意事项
#### 1. Spring Boot 2.6.X swagger接口列表不显示

> 这是因为Springfox使用的路径匹配是基于AntPathMatcher的，而Spring Boot 2.6.X使用的是PathPatternMatcher

需要在`application.yml`文件中追加如下配置：
```yaml
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
```
---
#### 2. Swagger2异常:Illegal DefaultValue null for parameter type integer 和 NumberFormatException: For input string: ""
> 从上面这句可以看出，有个默认值是空字符串的变量转换成Integer类型时异常。
> at io.swagger.models.parameters.AbstractSerializableParameter.getExample(AbstractSerializableParameter.java:412) ~[swagger-models-1.5.20.jar:1.5.20]

根据上面这句报错信息，点进去AbstractSerializableParameter.java:412可以看到
```java
if(BaseIntegerProperty.TYPE.equals(type)){
    return Long.valueOf(example);
}
```
就是说如果实体属性类型是`Integer`，就把example转为`Long`类型，而example默认为""，导致转换错误。

##### 解决办法：
###### 方法一
实体类中，Integer类型的属性加`@ApiModelProperty`时，必须要给example参数赋值，且值必须为数字类型。

```java
@ApiModelProperty(value = "编号", example = "1")
private Integer Id;
```

###### 方法二
忽略原版本的`swagger-annotations`和`swagger-models`，添加1.6.6版本的
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>apidoc-swagger-samples</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.7.0</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>swagger-v2-sample</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>

        <springfox-swagger.version>2.9.2</springfox-swagger.version>
        <swagger.models.version>1.6.6</swagger.models.version>
    </properties>

    <dependencies>
        <!-- 引入 Swagger 依赖 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${springfox-swagger.version}</version>
            <exclusions>
                <!-- 排除依赖 1.5.20版本，避免提示 java.lang.NumberFormatException: For input string: ""警告 -->
                <exclusion>
                    <groupId>io.swagger</groupId>
                    <artifactId>swagger-annotations</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>io.swagger</groupId>
                    <artifactId>swagger-models</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- 解决进入swagger页面报类型转换错误，排除1.5.20版本的引用，手动增加1.6.6版本 -->
        <dependency>
            <groupId>io.swagger</groupId>
            <artifactId>swagger-annotations</artifactId>
            <version>${swagger.models.version}</version>
        </dependency>
        <dependency>
            <groupId>io.swagger</groupId>
            <artifactId>swagger-models</artifactId>
            <version>${swagger.models.version}</version>
        </dependency>

        <!-- 引入 Swagger UI 依赖，以实现 API 接口的 UI 界面 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${springfox-swagger.version}</version>
        </dependency>
    </dependencies>
</project>
```