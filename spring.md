# Spring 
>需要导入的包：  
>commons-logging-1.2.jar；  
>spring-beans-5.1.0.RELEASE.jar;  
>spring-content-5.1.0.RELEASE.jar;  
>spring-core-5.1.0.RELEASE.jar;  
>spring-expression-5.1.0.RELEASE.jar;  

>关于解耦：
>耦合:类与类之间的依赖关系
>     方法和方法之间的依赖关系
>解耦:降低程序之间的依赖关系
>     实际开发中应该做到编译时不依赖是，运行时依赖
>解耦思路:
>     1.使用发射来创建对象，而避免使用关键字new。
>     2.通过读取配置文件来获取要创建的对象全限定类名。
## 1.给bean赋值  
Spring依赖注入是使用对应的set方法来对该属性赋值，如果变量命名不规范，会导致注入失败。
>比如命名一个aAaaa的变量，就会导致set,get方法写成setaAaaa，导致无法识别出来。

### 1.依赖注入的方式
### 2.p名称空间 
p:属性名称=value赋值
## 3.集合属性
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
## 4.工厂bean
