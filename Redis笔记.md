# Redis笔记
## 一、String类型的基本指令
### **1.set**
添加一个变量set key vlaue  
添加多个变量mset key value key value...
### **2.get** 
获取一个变量get key  
获取多个变量，get key key...
### **3.del**
删除一个变量del key  
删除多个变量mdel key key...
### **4.incr 增加** 
该数据增加1 incr key  
该数据增加value incrby key value  
该数据增加浮点数value incrbyfloat key value  
### **5.decr 减少同上4**
decr key  
decrby key vlaue  
decrbyfloat key vlaue 
### **6.设置时限数据**
设置一个数多少秒有效setex key seconds value  
设置一个数多少毫秒有效psetex key milliseconds value
>这是用来控制一些时效性的数据
### **7.string类型的操作注意事项**
①操作是否成功 成功返回(integer)1 失败返回(integer)0  
②运行结果值 返回(integer) value表示value个  
③结果为空时 (nil)  
④string最大存贮量为512M  
⑤数值计算的最大是 Java里math的long的最大值  
⑥redis用于各种*结构型*和*非结构型*的数据加速  
⑦key的命名习惯  `表名:主键名:主键值:字段名` 
|示例|表名:|主键名:|主键值:|字段名|
|:--:|:--:|:--:|:--:|:--:|
|1|order|id|234243|name|
|2|equid|id|675437|type|
|3|news|id|57904|title|
## 二、Hash操作
对一系列的存储的数据进行编组，方便管理；一个空间保存多个键值对数据，底层使用哈希表实现数据存储
### 1.hash类型数据的基本操作
添加/修改数据 hset key field value;  
获取数据 hget key field; hgetall key;  
删除数据 hdel key field [field2]...;  
添加删除多个数据 hmset key field1 value1 field2 value2...   
获取多个数据 hmget key field1 field2...  
获取哈希表中字段的数量 hlen key  
哈希表中是否存在该字段 hexists key value  
设置指定字段的数据增加指定返回的值 hkeys key;hvals key;

Hash类型数据扩展操作
获取哈希表中所有字段名或字段值  
hvals key  
hkeys key  
设置指定字段的数值数据增加指定范围的值  
hincrby key field increment  
hincrbyfloat key field increment  
>hash类型数据操作的注意事项  
>1.hash类型value只能存储字符串，不允许存储其他的数据类型，不能嵌套  
>2.每个hash可以存储2^32-1个键值对  
>3.hash不是为了存储大量对象而设计的，使用时应该慎重  
>4.hgetall可以获取全部属性，如果字段过多，遍历整个数据的效率就会很低

Hash的应用场景  

1.购物车的存储实例  
hmset 用户名字 商品名字 
hsetnx key field value 该key没有这个field和value时，添加这样的数据  
2.商家抢购  
程序：hmset p01 c20 100 c30 100 c50 110 c100 10  
     hincrby p01 c20 -1
     hincrby p01 c30 -10
>Redis提供数据和存储数据，不建议把业务压入redis

3.string存储对象json与hash存储对象优缺点  
string强调整体性,对外呈现；hash的*更新*操作更灵活;
## 三、list
数据存储需求：存储大量数据的，通过数据可以体现进入顺序  
list类型：保存多个数据，底层使用**双向链表**存储结构实现  
1.顺序表：有索引  
2.单向链表：插入新数据时，插入数据的尾节点指向下一个数据地址，然后上一个数据的尾节点指向这个数据的地址  
3.双向链表：与2相似   
### list类型数据的基本操作
1.添加修改数据  
lpush key value1 [value2] ...(左添加)  
rpush key value1 [value2] ...(右添加)  
2.获取数据  
获取list中start位到stop的数据 lrange key start stop  
lindex key index  
llen key  
3.获取并且移除数据  
lpop key  
rpop key  
4.list类型数据扩展操作  
在规定时间内获取并移除数据(b-block阻塞)
blpop key [kay02] timeout  
brpop key [kay02] timeout  
移除指定数据(操作不常用)  
lrem key count value 在key中删除count个value  
list多用于控制有先后顺序的数据；  
list的数据都是string类型，容量最大2^32-1；  
list具有索引概念，常以**队列**形式或者**栈**的形式进行操作；  
list可以对数据进行分页操作，通常第一页的信息是list，第二页更多信息从数据库加载;  
可以用于最新消息的展示
### set类型
存储要求：存储大量数据，在查询上提供更高的效率  
需要的结构：能保存大量数据，高效的内部存储机制，便于查询
在hash上进行变形，和hash完全相同，仅使用存储键，不存储值
1.添加数据 sadd key member1 (member2)

