<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    type="text/css" />
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/easyui/themes/icon.css"
    type="text/css" />

<title>Insert title here</title>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'综合管理系统',split:true"
        style="height: 100px;">
        <h1>综合管理系统</h1>
    </div>
    <div data-options="region:'south',title:'South Title',split:true"
        style="height: 100px;"></div>
    <div data-options="region:'west',title:'菜单',split:true"
        style="width: 250px;">
        <div id="rightMenu" class="easyui-accordion"
            data-options="fit:true,border:false,nimate:true,lines:true">
            <div title="搜索菜单"
                data-options="iconCls:'icon-search',collapsed:false,collapsible:false"
                style="padding: 10px;">
                <input id="layout_west_sc" class="easyui-searchbox"
                    data-options="prompt:'请输入你需要的菜单'"
                    style="width: 220px; height: 25px;">
            </div>
        </div>

    </div>
    <div data-options="region:'center',title:'首页'" class="easyui-tabs"
        id="tabs" style="padding: 0px; background: #eee;">
        <div title='首页'>首页</div>
        
        </div>
    <div data-options="region:'east',title:'快捷方式',split:true"
        style="width: 100px;"></div>

    <script type="text/javascript">
                    $.ajax({
                        type : 'POST',
                        dataType : "json",
                        url : 'user_getMenu.html',
                        success : function(data) {
                            $.each(data, function(i, n) {// 加载父类节点及一级菜单
                                var id = n.id;
                                $('#rightMenu').accordion('add', {
                                    title : n.text,
                                    iconCls : n.iconCls,
                                    selected : true,
                                    content : '<div style="padding:10px"><ul id="tree-'+ id + '" name="'+n.text+'"></ul></div>'
                                });
                                // 解析整个页面
                                $.parser.parse();
                                // 第二层生成树节点
                                if (!n.children || n.children.length == 0) {
                                    return true;
                                }
                                $("#tree-" + id).tree({
                                    data : n.children,
                                    //animate : true,
                                    lines : true,// 显示虚线效果
                                    onClick : function(node) {
                                        var tabTitle = node.name;
                                        var url = node.url;
                                        var icon = node.iconCls;
                                        var pid = node.parentId;
                                        if (node.state != 'closed') {
                                            addTab(tabTitle, url, icon, pid);
                                        }
                                    }
                                });
                            });
                        }
                    });

                    function addTab(title, url, icon, pid) {
                        if (pid) {
                            if ($('#tabs').tabs('exists', title)) {
                                $('#tabs').tabs('select', title);
                            } else {
                                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
                                $('#tt').tabs('add', {
                                    title : title,
                                    content : content,
                                    closable : true
                                });
                            }
                        }
                    }
                </script>
</body>
</html>