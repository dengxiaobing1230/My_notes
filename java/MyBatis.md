## 几个概念

​		框架( Framework )对于java来说,就是一系列为了**解决特定问题而定义的一系列接口和实现类**,在组织框架代码时,使用了一系列优秀的设计模式,使代码无论在性能上还是API操作上得到很大提升.框架可以看做是项目开发的半成品,基本的底层操作已经封装完毕,通过框架,程序员可以从底层代码中解脱出来,专注于业务逻辑的完成和性能的优化。

​		ORM（Object Relation Mapping）对象关系映射

​		MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。

​		Hibernate是一个全自动的ORM框架。因为Hibernate创建了Java对象和数据库表之间的完整映射，可以完全以面向对象的思想来操作数据库，程序员不需要手写SQL语句

## Mybatis的搭建

### 1、准备jar包

（数据库连接驱动，MyBatis核心包，Junit测试包，Log4j日志包）

```xml
<!--导入依赖-->
<dependencies>
    <!--mysql驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.21</version>
    </dependency>
    <!--junit驱动-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!--MyBatis-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.2</version>
    </dependency>
    <!--log4j-->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.12</version>
    </dependency>
</dependencies>
```

### 2、准备核心配置文件

mybatis.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
    <!--这里是别名，指向实体类,则Mapper.xml文件里不用写完整路径-->
    <typeAliases>
        <package name="com.deng.pojo"/>
    </typeAliases>
    <environments default="development">
        <!--指定环境-->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc_driver}"/>
                <property name="url" value="${jdbc_url}"/>
                <property name="username" value="${jdbc_username}"/>
                <property name="password" value="${jdbc_password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--这里的mapper文件扫描可以用<package name="com.deng.mapper"/>,两者不可同时用-->
        <mapper resource="com/deng/mapper/PlayerMapper.xml"/>
        <mapper resource="com/deng/mapper/ZoneMapper.xml"/>
        <mapper resource="com/deng/mapper/ClubMapper.xml"/>
    </mappers>
</configuration>
```

jdbc.properties

```properties
jdbc_driver=com.mysql.cj.jdbc.Driver
jdbc_url=jdbc:mysql://127.0.0.1:3306/testmybatis?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
jdbc_username=root
jdbc_password=000000
```

log4j.properties

```proper
#定义全局日志级别调试阶段推荐debug
log4j.rootLogger=debug,stdout
#包级别日志 调试阶段推荐debug
log4j.logger.com.bjsxt.mapper=debug
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.err
log4j.appender.stdout.layout=org.apache.log4j.SimpleLayout
log4j.appender.logfile=org.apache.log4j.FileAppender
log4j.appender.logfile.File=d:/bjsxt.log
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
log4j.appender.logfile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %l %F %p %m%n
```

### 3、其他配置文件

mybatisUtil.java

```java
public class MyBatisUtil {
    public static SqlSessionFactory sqlSessionFactory;
    static{
        try {
            String src = "mybatis.xml";
            InputStream inputStream= Resources.getResourceAsStream(src);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
    public static SqlSession getSqlSession(boolean autoCommit){
        return sqlSessionFactory.openSession(autoCommit);
    }
}
```

### 4、项目结构

```xml
<java>
	<com.deng>
		<mapper>
			UserMapper.xml
			UserMapper.java
        </mapper>
		<pojo>
			User
        </pojo>
		<util>
			MyBatisUtil.java
        </util>
    </com.deng>
</java>
<resources>
	jdbc.properties
	log4j.properties
	mybatis.xml
</resources>
```

### 5、MyBatis配置细节

*可以在官网上查看*

1.映射文件加载方式有？？？？？？？？？？？？？3种？

2、MyBatis查询的三种方式

返回单个对象，返回List对象集合，返回Map对象集合

## Mapper代理（取代传统DAO）

和上面一样，我没写传统DAO模式

传统DAO是由DAO层，由Interface接口和他的实现类组成，

而在Mapper里面，由Mapper取代其实现类完成了实现操作

### 1、mapper.xml

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.deng.mapper.ClubMapper">
    <update id="updateOne" parameterType="club">
        update club set zone=#{zone} where name=#{name}
    </update>
</mapper>
```



### 2、代理模式下的参数传递

#### 1、多参数传递

##### 单个基本数据类型

在mapper.xml里面

```xml
<select id="findAll" resultType="club">
	select * from club;
</select>
<select id="findOne" resultType="club">
	select * from club where id=#{para1};
</select>
<!--单个参数随便写什么，但是不能不写-->
<!--也可以使用#{arg0},从0开始，para从1开始-->
```

在mapper.java文件

```java
Club findOne(int a);
List<Club> findAll();
List<Emp> getByDeptnoAndSal(@Param("a") int deptno, @Param("b") double sal);
```

##### 多个基本数据类型

mapper.xml

```xml
<select id="getByDeptnoAndSal" resultType="emp">
    <!--select * from emp where deptno = #{arg0} and sal > #{arg1} -->
    <!--select * from emp where deptno = #{param1} and sal > #{param2} -->
    select * from emp where deptno = #{a} and sal > #{b}
</select>
<!--
	List<Emp> getByDeptnoAndSal(@Param("a") int deptno, @Param("b") double sal);
    多个基本数据类型作为方法参数
    Available parameters are [arg1, arg0, param1, param2]
    1 #{} 中可以写arg0  arg1  arg*  *参数的索引 从0开始 {10,1500}
    2 #{} 可以写 param1 param2 param* *参数的位置编号 从1开始
    3 #{}  接口上可以使用@Param注解为参数起别名 #{} 中可以放参数的别名
-->
```

mapper.java

```java
List<> getByDeptnoAndSal(@Para("a")int deptno,@Param("b")double sal);
```

##### 单个引用数据类型

mapper.xml

```xml
<!--
    List<Emp> getByDeptnoAndSal2(Emp emp);
    单个引用数据类型作为方法参数
    #{} 中要写对象的属性名
    -->
<select id="getByDeptnoAndSal2" resultType="emp" >
    select * from emp where deptno = #{deptno} and sal > #{sal}
</select>
```

mapper.java

```java
List<Emp> getByDeptnoAndSal2(Emp emp);
```

##### 多个引用数据类型

mapper.xml

```xml
<!--
    List<Emp> getByDeptnoAndSal3(@Param("a") Emp deptnoEmp,@Param("b") Emp salEmp);
    多个引用类型作为方法参数
    1#{} 中写法为  arg*.对象的属性名
    2#{} 中写法为  param*.对象的属性名
    3#{} 当参数列表中使用@Param添加别名 #{}中可以使用别名.对象的属性名
    -->
<select id="getByDeptnoAndSal3" resultType="emp">
    <!--select * from emp where deptno = #{arg0.deptno} and sal > #{arg1.sal}-->
    select * from emp where deptno = #{a.deptno} and sal > #{b.sal}
</select>
```

mapper.java

```java
List<Emp> getByDeptnoAndSal3(@Param("a") Emp deptnoEmp,@Param("b") Emp salEmp);
```

#### 2、模糊查询

进行模糊查询的时候可以使用concat（）来拼接参数和通配符。

Mapper.java

``` java
List<User> getByName(String name);
```

Mapper.xml

```xml
<select id="getByName" resultType="User">
    select * from user where name like concat(%,#{name},%);
</select>
```

test.java

```java
UserMapper mapper = sqlSession.getMapper(UserMapper.class);
mapper.getByName("SKT").forEach(System.out::println);
```

#### 3.自增主键回填

mapper.xml文件

```
<insert id="addTes" parameterType="club" useGeneratedKeys="true" keyProperty="id">
    insert into club values (DEFAULT,#{name},#{zone});
</insert>
<!--第二种方法-->
<insert id="addTest" parameterType="club">
    <selectKey order="AFTER" keyProperty="id" resultType="int">
        select @@identity
    </selectKey>
    insert into club values (DEFAULT,#{name},#{zone});
</insert>
```

`useGeneratedKeys`是否生成主键？

`keyProperty`表示主键的值类型

`order`表示在操作前还是操作后获取主键

`select @@identity`表示获取的identity，这个还不明白

### 3、动态SQL

#### 1、if标签

（需要and连接符）

```xml
<select id="selectOne">
    select * from club where 1=1
	<if test="id!=null">
		and id=#{id}>
	</if>
    <if test="name!=null">
		and name=#{name}
    </if>
</select>
```

#### 2、where标签

包裹住条件语句

```xml
<select id="selectOne">
    select * from club where 1=1
    <where>
    	<if test="id!=null">
			and id=#{id}>
		</if>
    	<if test="name!=null">
			and name=#{name}
    	</if>
    </where>
</select>
```

#### 3、choose语句

（前面的when条件成立，后面的when就不再判断了）

```xml
<select id="selectChoose" resultType="club">
    select * from club
    <where>
        <choose>
            <when test="id!=null">
                id=#{id}
            </when>
            <when test="name!=null">
                name=#{name}
            </when>
            <when test="zone!=null">
                zone=#{zone}
            </when>
        </choose>
    </where>
</select>
```

#### 4、set标签

（多用在UPDATE语句里面）

```xml
<update id="updateEmp" >
    update emp
    <set>
        <if test="ename != null and ename != ''">
           ename= #{ename},
        </if>
        <if test="job != null and job != ''">
           job= #{job},
        </if>
        <if test="mgr != null ">
            mgr= #{mgr},
        </if>
        <if test="hiredate != null ">
            hiredate= #{hiredate},
        </if>
        <if test="sal != null">
            sal= #{sal},
        </if>
        <if test="comm != null ">
            comm =#{comm},
        </if>
        <if test="deptno != null ">
           deptno= #{deptno},
        </if>
    </set>
    where  empno = #{empno}
</update>
```

#### 5、trim标签

（为了解决这些问题）

`prefix`要增加什么前缀

`prefixOverride`要去除什么前缀

`suffix`要增加什么后缀

`suffixOverrides`要去除什么后缀

```xml
<update id="updateTrim" parameterType="club">
    update club
    <trim prefix="set" suffixOverrides="," prefixOverrides="" suffix="">
        <if test="c2.id!=null">
            id=#{c2.id},
        </if>
        <if test="c2.name!=null">
            name=#{c2.name},
        </if>
        <if test="c2.zone!=null">
            zone=#{c2.zone},
        </if>
    </trim>
    <trim suffix="" prefixOverrides="and" suffixOverrides="" prefix="where">
        <if test="c1.id!=null">
            and id=#{c1.id}
        </if>
        <if test="c1.name!=null">
            and name=#{c1.name}
        </if>
        <if test="c1.zone!=null">
            and zone=#{c1.zone}
        </if>
    </trim>
</update>
```

#### 6、Foreach标签

用在传入的参数是集合类型和数组类型或者Map类型。

`item`表示集合中每一个元素进行迭代时的别名，

`index`用于表示在迭代过程中，每次迭代到的位置，

`open`表示该语句以什么开始，

`separator`表示在每次进行迭代之间以什么符号作为分隔 符，

`close`表示以什么结束

```xml
<select id="findByForeach" resultType="Club">
    select * from club where id in
    <foreach collection="list" item="id" open="(" separator="," close=")">
        #{id}
    </foreach>
</select>
```

里面的变量名和item里面的参数保持一致

#### 7、Bind标签

多用于**模糊查询的模板**（bind-->装订，模板）

```xml
<select id="getByBind" resultType="Club">
    <bind name="name" value="'%'+name+'%'"/>
    select * from club
    <trim prefix="where" suffix=";">
        name like #{name}
    </trim>
</select>
```

注意：在bind的标签里面，name的值，对应你想要传入的参数的值。

bind的作用是将一个值包装一番，再拿去使用。

#### 8、Sql片段标签

用途：将一个常用的SQL语句包装成一个sql标签方便以后引用；可以嵌套。

```xml
<!--test Sql -->
<select id="getBySQL" resultType="club">
    <bind name="name" value="'%'+name+'%'"/>
    <include refid="baseClubQuery"/>
    where name like #{name}
</select>
<!--Sql example-->
<sql id="clubColumns">
    id,name,zone
</sql>
<sql id="baseClubQuery">
    select 
    <include refid="clubColumns"/>
    from club
</sql>
```

### 4、手动处理映射关系

在Mapper.xml文件中

```xml
<resultMap id="studentMap" type="student">
    <id property="id" column="s_id"/>
    <result property="name" column="s_name"/>
    <result property="score" column="s_score"/>
    <association property="book" column="book" javaType="book">
        <id property="no" column="b_id"/>
        <result property="name" column="b_name"/>
    </association>
</resultMap>
```

#### 1、一对一关联查询

xml

```xml
<resultMap id="studentMap" type="student">
    <id property="id" column="s_id"/>
    <result property="name" column="s_name"/>
    <result property="score" column="s_score"/>
    <association property="book" column="book" javaType="book">
        <id property="no" column="b_id"/>
        <result property="name" column="b_name"/>
    </association>
</resultMap>
<select id="getMyBook" resultMap="studentMap">
    select *
    from student
    left join book
    on s_book=b_id
    where s_id=#{id}
</select>
```

<font color=red size="6">不知道如何解决多表关联查询时，不同表之间同名字段引起的冲突</font>

#### 2、一对多查询

```xml
<resultMap id="studentMap" type="student">
    <id property="id" column="s_id"/>
    <result property="name" column="s_name"/>
    <result property="score" column="s_score"/>
    <collection property="book" ofType="book">
        <id property="no" column="b_id"/>
        <result property="name" column="b_name"/>
    </collection>
</resultMap>
<collection>
```



#### 3、多对多查询

```xml
<resultMap id="studentMap" type="student">
    <id property="id" column="s_id"/>
    <result property="name" column="s_name"/>
    <result property="score" column="s_score"/>
    <collection property="book" ofType="book">
        <id property="no" column="b_id"/>
        <association property="record" javaType="record">
            <id property="no" column="r_id"/>
            <result property="name" column="r_name"/>
        </association>
    </collection>
</resultMap>
<collection>
```

collection关联的是两个多对多对象的关系类