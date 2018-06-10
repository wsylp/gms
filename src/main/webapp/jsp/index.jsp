<!DOCTYPE html>

<html>
<head>
<title>Index</title>
<meta name="viewport" content="width=device-width" />
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
</head>
<body class="easyui-layout">
    <div id="OverTimeLogin" class="easyui-dialog"
        data-options="closed:true,modal:true">
        <iframe width="726px" scrolling="no" height="497px"
            frameborder="0" id="iOverTimeLogin"></iframe>
    </div>
    <div data-options="region:'north',border:false,split:true"
        style="height: 60px;">
        <div class="define-head">
            <div class="define-logo">

                <div id="LoginBotoomLine">综合管理系统</div>
            </div>
            <div class="define-account"></div>
        </div>
    </div>
    <div data-options="region:'west',split:true,title:'菜单列表'"
        style="width: 250px; height: 100%; padding-top: 2px; background-color: #fff; overflow: auto">
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
    <div data-options="region:'south',border:false"
        style="height: 20px;">
        <div class="define-bottom"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="mainTab" class="easyui-tabs" data-options="fit:true">
            <div title="我的桌面" data-options="closable:true"
                style="overflow: hidden; background: #fff">
                <iframe scrolling="auto" frameborder="0" src=""
                    style="width: 100%; height: 100%;"></iframe>
            </div>
        </div>
    </div>
    <div id="tab_menu" class="easyui-menu" style="width: 150px;">
        <div id="tab_menu-tabrefresh"
            data-options="iconCls:'icon-reload'">刷新</div>
        <div id="tab_menu-openFrame">在新的窗体打开</div>
        <div id="tab_menu-tabcloseall">关闭所有</div>
        <div id="tab_menu-tabcloseother">关闭其他标签页</div>
        <div class="menu-sep"></div>
        <div id="tab_menu-tabcloseright">关闭右边</div>
        <div id="tab_menu-tabcloseleft">关闭左边</div>
        <div id="tab_menu-tabclose" data-options="iconCls:'icon-remove'">
            关闭</div>
        <div id="menu" class="easyui-menu" style="width: 150px;">
        </div>
    </div>

    <script type="text/javascript">
                    $(function() {
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
                                  
                                        console.log(n.children);
                                    $("#tree-" + id).tree({
                                        data : n.children,
                                        //animate : true,  
                                        lines : true,// 显示虚线效果  
                                        onClick : function(node) {
                                            if (node.url) {
                                                var tabTitle = node.text;
                                                var id = node.id;
                                                var url = node.url;
                                                var openMode = node.state;
                                                var icon = node.iconCls;
                                                var pid = node.id;
                                                addTab(id,tabTitle, url, openMode, icon, pid);
                                            }
                                        }
                                    });
                                });
                            }
                        });
                    });

                     
                    function addTab(id,title, url, openMode, icon, pid){
                        if ($('#mainTab').tabs('exists', title)){
                            $('#mainTab').tabs('select', title);
                        } else {
                            var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
                            $('#mainTab').tabs('add',{
                                id:id,
                                title:title,
                                content:content,
                                closable:true
                            });
                        }
                    }
                </script>
</body>
</html>