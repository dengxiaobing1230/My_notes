# java初级阶段

## 1.static关键字

* 可以修饰**变量**，**方法**，**代码块**，**内部类**，不能修饰**构造方法**。

    * static修饰的成员变量为静态成员变量（类变量）。与类的生命周期相同，是整个应用执行期间。

    * 可以用`对象.静态变量`的方法来获取静态变量，但是不建议。
    
* 访问权限

    * 静态方法不可以访问非静态的方法，变量，也不能访问`this`

    > 可能存在这样一种情况，静态方法已经随类被加载了，但是没有实例化，也就没有成员变量和成员方法，不能读取。

    * 非静态的方法可以访问静态的变量和方法。

    > 因为静态方法已经先于非静态方法被加载进去。

* 存储位置

    * 静态成员变量存储在**方法区**中
    * 实例成员变量存储在**堆内存**上
    
* 代码块（按顺序自动执行，不可以被调用）

    * 局部代码块（方法内）
    
    局部代码块定义的变量作用范围只限于代码块以内。
    
    * 成员代码块（类内）
    
    执行时间先于构造方法。依照代码块的顺序执行，运用很少（可用于匿名内部类，将初始化代码放入代码块里）
    
    * static代码块
    
    在类第一次加载的时候，执行static代码块（只一次）。运用比较多，**可以用于执行全局化的初始化操作**，比如创建工厂和加载数据库初始信息。

##  2.生成DOC文档（IDEA）

Tools-->Generate Java

![alt 生成DOC](\Generate_JavaDoc.png)

## 3.权限修饰符

| ***修饰符*** | ***同一个类*** | ***同一个包中(同包可继承)*** | ***子类***  | ***所有包的所有类*** |
| :----------: | :------------: | :--------------------------: | :---------: | :------------------: |
|   private    |  ***\**\****   |                              |             |                      |
| 默认/default |  ***\**\****   |         ***\**\****          |             |                      |
|  protected   |  ***\**\****   |         ***\**\****          | ***\**\**** |                      |
|    public    |  ***\**\****   |         ***\**\****          | ***\**\**** |     ***\**\****      |

## 4.this关键字

创建对象分为一下四步：

 	1.分配对象空间，将对象成员初始化为null或0；  

 	2.执行属性值显示初始化；  

 	3.执行构造方法；

 	4.返回对象的地址给相关变量。

**this 是指创建好的对象的地址。**

**可以使用this调用构造方法来实现重载的功能，但是必须放在第一行。**

## 5.继承

* 子类继承了父类的全部方法（**除了构造方法**）和属性，但是不一定可以访问。

* 子类重写了的父类的方法，其访问权限修饰符的范围不能小于父类。



## 6.多态

父类能出现的地方，子类一般也能出现，子类出现的地方，父类不能出现。

* 向上转型 
* 向下转型 需要强制类型转换。（转换后仍然有可能报错）

## 7.代码块

在有继承情况时，

父类子类都有 **静态代码块** ，**普通代码块** ，**构造方法**时，加载的顺序是：

​	父类静态代码块 --> 子类静态代码块 --> 父类普通代码块 -->  父类构造方法 --> 子类普通代码块 -->子类构造方法，例如：

<img src="C:\Users\邓小兵\Documents\我的Java笔记\Static_Block_list.png" alt="执行顺序" style="zoom:60%;" />

## 8.接口

1. 用static修饰的方法必须要写方法体，子类实例可以调用此静态方法；

   static修饰的属性必须是静态常量，子类实例不可调用，子类可以调用。

2. 1.8之前：接口的属性都是静态常量（static final），方法不可以使用static修饰。

3. 实现类需要实现接口中定义的所有抽象方法。

### 1.在JDK1.7以前的接口的变量。

都是`public static final  ` ,全局静态常量。

接口内都是abstract方法，不可以使用`static`修饰（abstract与static ,final ,private不能共存）

### 2.接口中可以添加非抽象方法（default）。

实现类可以重写，但是必须去掉（default）,只能通过对象名调用。不可以使用`接口名.方法`调用。

```java
interface Father{
    
    default void getName(){
        System.out.println("接口的default方法");
    }
}
class Son implements Father {

    @Override
    public void getName() {
		System.out.println("实现类重写了接口的default方法");
    }
}
```

> 在JDK9及其以后，接口可以定义private的非抽象方法，便于多个方法的提取冗余代码。同时也实现了代码隐藏。

## 9.抽象类

实现类一定要重写全部的抽象方法。

抽象类可以有非抽象方法。

## 10.内部类

内部类的特征：

可以使用四个权限修饰符；

外部类是不能直接使用内部类的成员变量和成员方法的；

1. **静态成员内部类**

   实例化的格式是：

```java
外部类.静态内部类 对象名 = new 外部类.静态内部类();
```

2. **非静态成员内部类**（局部内部类）

   内部类可以直接使用外部类的成员变量和成员方法；

   区分外部类和内部类的同名变量是`外部类名.this.变量名`；

3. **局部内部类**（这个类在方法体里面）

   使用final关键字修饰，可不写。

4. **匿名内部类**（直接new 接口/抽象类(){//内部实现}）

   内部类简化写法，是一种特殊的局部内部类。

## 11.异常

### 1.try……catch……finally标准结构

​	try用来包裹住需要捕获的代码；catch用来匹配异常对象进行处理；finally是在不论是否执行catch，都会执行finally后面的代码。而且可以在return执行完成后，继续执行finally里的语句代码。

### 2.检查时异常和运行时异常的区别？？ 

1. **检查时异常**：必须捕获进行处理，不捕获不能运行。

	2. **运行时异常**：不一定要进行处理，系统自动检测进行处理。

### 3.异常 可以手动抛出。

```java
throw new Exception();
//throws是方法和类用来向上抛出异常。
//throw是用在语句前面的。
```

## 12.类于类之间的关系

1. **继承**（也叫泛化，Generalization）

   extends，不多说。

2. **实现**（Realization）

   implements，也不多说。

3. **依赖**（Dependency）

   弱关系，这种关系具有偶然性，临时性，一个类作为另一个类的方法参数或者局部变量。

4. **关联**（Association）

   强于依赖关系，属于必然的，长期的，强烈的，（个人理解是成员变量的关系。）

5. **聚合**（Aggregation）

   一种关联关系的特例，是整体和部分的关系。

6. **组合**（Composition）

   一种关联关系的特例，强于聚合，不可分割的部分。

## 13.面向对象设计原则

7种原则：

1. **单一职责原则**：系统中的每一个类都应该是只有一个职责。
2. **开闭原则**：软件实体应该对扩展开放，对修改关闭；即引入新功能而不改变原代码。
3. **里氏替代原则**：父类可以出现的地方，则子类也可以出现。反过来不一定了。
4. **依赖倒置原则**：通过抽象（抽象类或则接口）来使各个类和模块的实现相互独立，互不影响。
5. **接口分离原则**：客户端不应该强行依赖它不需要的接口，类间的依赖应该建立在最小的接口上。
6. **迪米特法则**：如果两个类不必直接通信，则就不应该发生直接互相作用；如果一个类需要用到这里的另一个类，则可以通过第三者转发这个调用。
7. **合成聚合原则**：优先使用对象组合，而不是类继承。

## 14.常用类

1. 自动装箱和自动拆箱：

   在范围-128到127之间，的自动装箱会让对象引用指向同一个地址。

   包装类作为参数，是引用类型。

   包装类的value参数是final修饰的，不可修改。

2. StringBuffer和StringBuilder的不同。

   StringBuffer线程不安全效率高，StringBuilder线程安全但是效率不高。

## 15.设计模式

1. 单例模式

   私有构造方法，使用静态方法调用构造方法返回一个对象

   ```java
   //懒汉模式	调用时分配空间
   class Demo{
       private static final Demo demo;
       private Demo(){}
       public static Demo getInstance(){
           if(Demo == null){
               demo = new Demo();
           }
           return demo;
       } 
   }
   //饿汉模式	创建时分配空间
   class Demo{
       private static Demo demo = new Demo();
       private Demo(){}
       public static Demo getInstance(){
           return demo;
       } 
   }
   ```

 ## 16.数据结构

 ### 算法

如何判断算法优劣？

​	

> 其依据是复杂度。
>
> 复杂度是指：在计算机上运行该算法的时候，所需要占用的资源是多少。
>
> 分为：时间复杂度 和 空间复杂度
>
> 时间复杂度：计算机在执行算法所需要计算机执行的工作量。
>
> 空间复杂度：计算机在执行算法的时候所需要占用的空间内存。

   1. 选择排序算法：排升序，分两段，前面无序，后面有序，循环比较，大的和后面的交换。

   2. 冒泡排序：后面小的，相邻的互相交换，把大的放到后面。

   3. 二分查找：

      ```java
      	public int getIndex(int[] array,int count){
              int start = 0;
              int end = array.length-1;
              int mid = end/2;
              while(start!=end){
                  if(count>array[mid]){
                      start = mid+1;
                  }else if(count<array[mid]){
                      end = mid-1;
                  }else{
                      return mid;
                  }
              }
              return -1;
          }
      ```

### 几种数据类型：

1. 顺序表 特点：存储在连续的内存地址上
2. 链表  特点：数据的存储不一定在一块连续的内存空间中，每一个节点对应一个数据元素，由数据域和指针域组成。元素间的逻辑关系通过存储节点之间的链接关系反映出来。
3. 栈   特点：是一种特别的顺序表，必须按照顺序读取，先存入的数据后取出。
4. 队列  特点：按照顺序读取，先存入的先取出。
5. 树   特点：一种特别的链表，他只有一个父节点，但是可以有不止一个的子节点。
6. 图   特点：一种网状数据结构，由非空顶点集合和一个描述顶点间关系的集合组成。

### 集合

#### 集合框架

* Collection接口储存一组不唯一，无序的对象。
* List接口存储一组不唯一，有序的对象。
* Set接口存储一组唯一，无序对象。
* Map接口存储一组键值对象，提供key到value的映射
  * key无序，唯一
  * value无序，不唯一

#### List和Set的主要实现类

* **ArrayList**

  * 在内存中分配连续的空间，实现长度可变的数组。

  * 遍历与随机访问的效率高

  * 增删、按内容查询效率低

  * 底层是数组实现

  * JDK1.7使用无参构造创建ArrayList的时候，默认数组长度为10。JDK1.8的时候是0；

  * 方法：
    * add(Object o)，增加一个元素
    * size()，获取长度
    * remove(int index)，删除该位置的元素。
    * contains(Object o)，是否包含。。。。。
    
    * iterator()，返回这个集合的迭代器

* **LinkedList**

  * 底层是双向链表实现

  * 增删节点快，索引较慢

  * 相比较ArrayList而言，多出了用来操作首尾元素的方法。

    addFirst()	addLast()	removeFirst()	removeLast()	getFirst()	getLast()

* **Queue 队列**

  以前是由Vector实现的，但是现在Vector过时了。现在是使用

  `public Interface Queue<E> extends Collection<E>`实现类有ArrayDeque.

* **Deque 双向队列**

  `public Interface Deque<E> extends Collection<E>`实现类有LiskedList

* **HashSet（无序，唯一）**

  * 采用Hashtable哈希表存储结构

  * 增删快，查询快。
  * 无序

* **LinkedHashSet** 

  * 采用哈希表存储结构，使用链表维护次序。
  * 有序

* **TreeSet**

  * 采用二叉树结构存储（红黑树）
  * 有序 查询速度比List快（按内容查询）
  * 查询速度略慢于HashSet
  * 数据结构维护复杂

* **List和Set的遍历方式**

  for循环(**Set没有**)	for-each循环	Iterator迭代器	流式编程forEach

#### Hash表

#### hashCode和equals方法

hashCode计算哈希码；equals计算是否相同

#### HashMap

采用Hashtable存储结构

增删速度快	查询速度快	

key无序

键唯一	值不唯一

键只可以有一个NULL，值可以有多个NULL。

#### LinkedHashMap

在使用哈希表存储结构的同时，也用链表维护次序

key有序

#### TreeMap

采用红黑树存储

查询速度快于List（按内容查询），略慢于HashMap

key有序（默认升序）

key不可以是NULL	value可以是NULL

​	方法：

* `map.put("CHN","CHINA");`在map里面插入键值对
* `map.get("CHN");`获取对应键的值
* `map.contrainsKey("CHN");`是否包含对应的键值
* `map.isEmpty();`
* `map.remove();`
* `map.size()`

#### 新一代并发集合类

ConcurrentHashMap >>> HashMap

CopyOnWriteArrayList >>> ArrayList

CopyOnWriteArraySet >>> HashSet

#### 泛型

1. 上限通配符与下限通配符

	```java
class A{}
class B extends A{}
class C extends B{}
class Test{
    public static void main(String[] args){
        ArrayList<? extends B> c;
        //这里的“？”定义的是一个继承自B类的类型
        ArrayList<? super B> a;
        //这里的“？”定义的类型是B的超类
    }
}
	```

2. 方法重载时，不同的泛型不代表不同参数，不可以视作重载

   ```java
   class Test<T>{
       private T getType(T t){}
    private K getType(K k){}
   }//这样的不是重载
   ```
   

#### 函数式接口

​		可以有许多的其他的方法，但是**只能有一个抽象方法**的接口或者抽象类。JDK8提供了一个注解@FunctionalInterface来进行编译检查。

##### 内置的函数式接口

|     **函数式接口**      | **方法名**          | **输入参数** | **输出参数** |                  **作用**                  |
| :---------------------: | ------------------- | ------------ | ------------ | :----------------------------------------: |
| **消费型接口Consumer**  | void accept(T t)    | T            | void         |          对类型为T的对象进行操作           |
| **供给型接口Supplier**  | T get（）           | void         | T            |             返回类型为T的对象              |
| **函数型接口Function**  | R apply（T t）      | T            | R            | 对类型为T的对象进行操作，返回类型为R的对象 |
| **断言型接口Predicate** | boolean test（T t） | T            | boolean      | 对类型为T的对象进行操作，返回布尔类型结果  |

##### 方法引用

```java
new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        };
//=======等同于=======>
()-{
    System.out.println(o);
}
//=======等同于=======>
System.out::println;//::后面的是方法，但是不写参数列表
```

一般的Lambda表达式`()->{}`，根据方法的引用，可以用`类名::方法名`和`变量名::方法名`的方式来调用其他的类的静态方法体作为自己的方法体，前提是自己和他的方法返回值和参数列表完全一致。

##### 流式编程

这是对容器对象功能的增强，不同于字节流，字符流。

Stream3步操作：

1. 创建Stream；
2. 中间操作：处理Stream；
3. 终止操作：产生结果；

```java
List<Integer> list = new ArrayList<>();
Collections.addAll(list,1,2,3,4,5,6,7,8,9);
Stream<Integer> stream = list.stream();					//创建操作
stream = stream.filter(x->60 >= x)						//处理操作
.distinct()
.sorted((x1,x2)->{return -Integer.compare(x1,x2);})
.map((x)->x+5)
.skip(2);
 System.out.println(stream.findFirst());				//终止操作
```

## 17.IO流

### IO流的方向

​		**（判断依据是数据相对于程序的方向）**

​		输入流：数据流向是从数据源到程序（以InputStream，Reader相关的流）

​		输出流：数据流向是从程序到数据源（以OutputStream，Writer相关的流）

```mermaid
graph LR
A[数据源]-->|输入流| B[程序]	
B-->|输出流|A
```

​		字节流：以字节为单位获取数据。如：InputStream，OutputStream。

​		字符流：以字符为单位获取数据。如：Writer，Reader。

