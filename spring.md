# Spring 
>需要导入的包：  
>commons-logging-1.2.jar；  
>spring-beans-5.1.0.RELEASE.jar;  
>spring-content-5.1.0.RELEASE.jar;  
>spring-core-5.1.0.RELEASE.jar;  
>spring-expression-5.1.0.RELEASE.jar;  

>关于解耦：
>耦合:类与类之间的依赖关系
>方法和方法之间的依赖关系
>解耦:降低程序之间的依赖关系
>实际开发中应该做到编译时不依赖是，运行时依赖
>解耦思路:
>1.使用发射来创建对象，而避免使用关键字new。
>2.通过读取配置文件来获取要创建的对象全限定类名。   

IOC创建对象方式。



## 一、给bean赋值  
>Spring依赖注入是使用对应的set方法来对该属性赋值，如果变量命名不规范，会导致注入失败。
>比如命名一个aArea的变量，就会导致set,get方法写成setaArea，导致无法识别出来。

### 1.IOC创建对象的方式

1.无参构造方法实例化对象,默认  

```XML
<bean id="user" class="com.deng.pojo.User">
    <property name="name" value="邓"/>
</bean>
```

> 这里是使用setName方法给name赋值，此时com.deng.pojo.User里面必须要有无参构造方法。

2.有参方法实例化对象，也可以  

第一种

```xml
<bean id="user" class="com.deng.pojo.User">
	<constructor-org index="0" value="邓小兵"/>
</bean>
```

> 这里的index指的是有参构造方法的参数的位置，value表示的是赋的值，还可以使用type来指定复制的类型。

第二种（不建议使用）

```xml
<bean id="user" class="com.deng.pojo.User">
	<constructor-org type="java.lang.String" value="邓小兵"/>
</bean>
```

> 这个是根据参数类型去自动匹配合适的参数

第三种（重点）

```xml
<bean id="user" class="com.deng.pojo.User">
	<constructor-org name="name" value="邓小兵"/>
</bean>
```

当要注入的值是引用类型时间，可以使用ref指定一个bean的id，来代替value赋值。例如

给user指定一个别名

2.bean 

id：bean的唯一标识符	class：bean对象所对应的全限定名 	name：可以取多个别名

3.import

```xml
<import resource="applicationContext.xml"/>
```

可以导入其他的bean文件里面的内容

### 3.DI依赖注入

1.构造器输入

2.set方式注入

3.拓展方式

### 2.p名称空间 

p:属性名称=value赋值
### 3.集合属性
1.list  
```xml
<list>
    <entry>
        <key></key>
        <value></value>
    </entry>
</list>
```
2.map  
```xml
<map>
    <entry>
        <key></key>
        <value></value>
    </entry>
</list>
```
3.集合类型的bean(需要添加命名空间util)  
```xml
<util:list>
    <ref bean="">
    <ref bean="">
    <ref bean="">
    
</util:list>
```
### 4.工厂bean

# 二、部分笔记

1.RequestParam注解

defaultValue：默认值

name和value是同一个意思：这个变量的别名

required：是否是必须的值，如果没有，则报404找不到