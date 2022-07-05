### gateway使用均衡负载报503错误

由于springcloud2020弃用了Ribbon，因此Alibaba在2021版本nacos中删除了Ribbon的jar包，因此无法通过lb路由到指定微服务，出现了503情况。

所以只需要引入springcloud loadbalancer包即可

```xml
<!--客户端负载均衡loadbalancer-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

参考地址：[https://blog.csdn.net/qq_41953714/article/details/116239716]()

---