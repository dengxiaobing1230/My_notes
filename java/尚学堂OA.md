## 添加数据

java service page里面绑定一个点击事件：

```jsp
<script>
$('send'){
	var data1 = $('#data1').val();
	var data2 = $('#data2').val();
	var data3 = $('#data3').val();
	//收集完信息
	var datapack = {data1:data1,data2:data2,data3:data3,methodName:'处理Ajax的方法的名字'};//打成一个包
	//发送Ajax请求
	$.get('DeptServlet', datapack, function(data){//依次是servlet名字，数据包，返回执行的方法以及返回参数data
		if(eval(data)){
            alert("Success");
        }else{
            alert("Failure");
        }
	}
}
</script>
```

Servlet.java

```java
String data1 = req.getParameter("data1");
//等等之类的
DeptService dept = new DeptServiceImpl();
int i = dept.deptAdd(data1,data2);
resp.getWriter.write(i==1?"true":"false");
```

service.java

```java
public int deptAdd(Dept dept) {
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
    int i = mapper.deptAdd(dept);
    sqlSession.commit();
    return i;
}
```

Mapper.xml，java略

```xml
<insert id="deptAdd">
    insert into dept values(#{deptno}, #{deptname}, #{location})
</insert>
```



## 读取数据并分页

`$(function() {});`是`$(document).ready(function(){ })`的简写，这是一种页面加载完毕后立即执行的函数。

```jsp
<script>
    //这里指定往dept里面传数据
    $.get("DeptServlet",{methodName:'deptList'},function(data){
		eval("var arr="+data);
        for(var i = 0; i < arr.length; i++){
                    $('tbody').append("<tr>\n" +
                        "            <td><input name=\"\" type=\"checkbox\" value=\"\" /></td>\n" +
                        "            <td>"+arr[i].deptno+"</td>\n" +
                        "            <td>"+arr[i].deptname+"</td>\n" +
                        "            <td>"+arr[i].location+"</td>\n" +
                        "            <td><a href=\"DeptServlet?methodName=deptUpdateInfo&deptno="+arr[i].deptno+"\" class=\"tablelink\">修改</a> &nbsp;&nbsp;&nbsp;&nbsp;  " +
                        "<a href=\"#\" class=\"tablelink click deptDel\"> 删除</a></td>\n" +
                        "        </tr>")
                }
	});
    //使用on给特定的class设置点击事件，这里的是删除操作的代码
    $(document).on('click','deptDel',function(data){
        if(confirm("确认删除吗？")){
            var deptno = $(this).parent().parent().find('td').eq(1).text();

            $.get('DeptServlet', {methodName:"deptDelete", deptno:deptno}, function(data){
                if(eval(data)){
                    alert('部门删除成功！');
                    //页面刷新一下，重新走页面加载函数，就会重新发ajax
                    window.location.reload();
                }else{
                    alert('部门删除失败！');
                }
            });
        }
        return false;//超链接不跳转了
    });
</script>
<!--使用on给特定的class设置点击事件-->
```

servlet.java

```java
public void deptList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //设置返回的类型，以前的是普通文本，现在的是网页文件
    resp.setContentType("text/html;charset=utf-8");

    //2.调用service处理
    DeptService deptService = new DeptServiceImpl();
    List<Dept> list = deptService.deptList();
	//传回的是一个集合,以JSON的格式传出去的
    resp.getWriter().write(new Gson().toJson(list));
}
```

