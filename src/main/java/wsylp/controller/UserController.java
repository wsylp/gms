package wsylp.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import wsylp.service.UserService;
import wsylp.vo.MenuTree;

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

    @Autowired
    private UserService userService;

}
