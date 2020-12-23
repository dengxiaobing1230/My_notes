## springboot

### 1.项目创建

1.导入父级

```xml
<parent>
    <artifactId>spring-boot-starter-parent</artifactId>
    <groupId>org.springframework.boot</groupId>
    <version>2.4.1</version>
</parent>
<!--如果已经有其他的父级，则使用依赖来替代-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.1.13.RELEASE</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

2.导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

3.创建启动类

```java
//启动类的位置是"src/main/java/com/deng下面"
@SpringBootApplication
public class MySpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class,args);
    }
}
```

4.其他的地方和springmvc一样

### 1.整合mybatis

导入依赖

```xml
<!--mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
<!--mysql-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.21</version>
</dependency>
```

2.配置文件

```yml
## 文件地址"src/main/resources/application.yml"
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/myweb?characterEncoding=utf8&&useSSL=false&serverTimezone=GMT%2B8&&allowPublicKeyRetrieval=true
    username: root
    password: "000000" ## 在"mysql8.0.21"中,需要使用双引号来""标记密码，不然不生效
    driver-class-name: com.mysql.cj.jdbc.Driver
mybatis:
  mapper-locations: classpath:mybatis/*.xml
  type-aliases-package: com.deng.entity
```

