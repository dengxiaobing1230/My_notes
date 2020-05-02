## SQL常用的库命令  
1.查看数据库列表: `show databases`;  
2.创建数据库: `create database db_name`;  
3.选择数据库: `use db_name`;  
4.删除数据库: `drop database db_name`; 
>删除前可先判断该数据库是否存在`drop database if exits db_name`  
## SQL常用的表命令
1.创建数据表: `create table tb_name`;  
2.查看数据表列表: `show tables`;  
3.查询字段: `select 字段1,字段2…… from tb_name`;  
4.删除数据表:  
## SQL语法
### 1.SELECT语句
用于从表中选取数据  
>1.选择部分字段: `select 字段1,字段2.... from tb_name`;  
>2.选择全部字段: `select * form tb_name`;  
### 2.DISTINCT语句  
用于表中返回唯一的不同的值  
>1.`select distinct 列名 form tb_name`;  
### 3.WHERE语句  
按照某种条件从表中选取某种数据, <,>,=,<=,>=原意.
| 操作符 | 描述 |
|:----:|:-----:|
|<>|不等于|
|BETWEEN|在某个范围内|
|LIKE|搜索某种模式|
