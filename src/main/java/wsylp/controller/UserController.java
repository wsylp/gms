package wsylp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wsylp.filter.Pagination;
import wsylp.filter.UserSearchFilter;
import wsylp.po.User;
import wsylp.service.UserService;
import wsylp.vo.MenuTree;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wsylp on 2017/5/4.
 */
@Controller
public class UserController extends BaseController {

    private static Logger logger = LogManager.getLogger(UserController.class.getName());

    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }

    @RequestMapping("/userList.html")
    public String userList() {
        return "userList";
    }

    @RequestMapping("user_getMenu.html")
    public void getMenu() throws IOException {
        List<MenuTree> menuTree = userService.getMenuTree();
        String json = JSON.toJSONString(menuTree);
        response.getWriter().write(json);
    }

    @RequestMapping("user_getUserList.html")
    public void getUserList() throws IOException {


        String realName = request.getParameter("realName");
        String loginName = request.getParameter("loginName");
        String orgCode = request.getParameter("orgCode");
        UserSearchFilter filter = new UserSearchFilter();
        filter.setLoginName(loginName);
        filter.setRealName(realName);
        filter.setOrgCode(orgCode);


        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Pagination pagination = new Pagination();
        pagination.setLimit(Integer.valueOf(rows));
        pagination.setPage(Integer.valueOf(page));




        List<User> users = userService.getUserList(filter,pagination);
        int count = userService.countUserList(filter);

        HashMap<String,Object> map = new HashMap<>();
        map.put("rows",users);
        map.put("total",count);
        String json = JSON.toJSONString(map);
        response.getWriter().write(json);
    }


    @Autowired
    private UserService userService;

}
