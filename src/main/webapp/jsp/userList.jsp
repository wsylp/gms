<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascripts/jquery-3.2.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js"></script>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/easyui/themes/default/easyui.css"
          type="text/css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/easyui/themes/icon.css"
          type="text/css"/>
    <title>Insert title here</title>
</head>
<body>
<div style="margin:20px 0;">
    <form  >
        <label for="realName">姓名：</label>
        <input id="realName" name="realName" class="easyui-textbox" style="width:200px;"/>

        <label for="loginName">登录号：</label>
        <input id="loginName" name="loginName" class="easyui-textbox" style="width:200px;"/>

        <label for="orgCode">机构：</label>
        <input id="orgCode" name="orgCode" class="easyui-textbox" style="width:200px;"/>

        <a id="search" name="search" class="easyui-linkbutton" onClick="searchUsers()"  iconCls="icon-search">查询</a>
    </form>


</div>

<table class="easyui-datagrid" id="userList" title="用户汇总"  pagination="true"
       data-options="singleSelect:true,collapsible:true,method:'get',rownumbers:true"   fitColumns="true" url="user_getUserList.html">
    <thead>
    <tr>

        <th data-options="field:'realName',width:80">姓名</th>
        <th data-options="field:'loginName',width:100">登录名</th>
        <th data-options="field:'orgCode',width:80,align:'right'">机构号</th>
        <th data-options="field:'phone',width:80,align:'right'">手机号</th>
        <th data-options="field:'idCard',width:250">身份证号</th>
    </tr>
    </thead>
</table>

<script type="text/javascript">
    $.parser.parse();
   var realName =  $('#realName').textbox('getValue');
   var loginName = $('#loginName').textbox('getValue');
   var orgCode = $('#orgCode').textbox('getValue');



    $('#userList').datagrid({
        queryParams: {
            realName: realName,
            loginName:loginName,
            orgCode:orgCode
       }

    });

    function searchUsers(){
        var realName =  $('#realName').textbox('getValue');
        var loginName = $('#loginName').textbox('getValue');
        var orgCode = $('#orgCode').textbox('getValue');
        $('#userList').datagrid('load',{
            realName: realName,
            loginName:loginName,
            orgCode:orgCode

        });
    }

</script>
</body>


</html>