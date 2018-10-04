package wsylp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wsylp.dao.MenuMapper;
import wsylp.dao.RoleMapper;
import wsylp.dao.UserMapper;
import wsylp.filter.Pagination;
import wsylp.filter.UserSearchFilter;
import wsylp.po.Menu;
import wsylp.po.Role;
import wsylp.po.User;
import wsylp.service.UserService;
import wsylp.util.TreeNoteUtil;
import wsylp.vo.MenuTree;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;
    
    @Autowired
    private RoleMapper roleDao;

    @Autowired
    private MenuMapper menuDao;

    @Override
    public List<MenuTree> getMenuTree() {
        // 1：获取一级手风琴菜单
        List<Menu> list = menuDao.getMenus();
        List<MenuTree> treelist = new ArrayList<>(); 
        for(Menu m :list ) {
            MenuTree tree = new MenuTree();
            BeanUtils.copyProperties(m, tree);
            treelist.add(tree);
        }
        List<MenuTree> fatherNode = TreeNoteUtil.getFatherNode(treelist);
        return fatherNode;
    }

    @Override
    public List<Role> getRolesByLoginName(String loginName) {
       List<Role> roleList = roleDao.getRolesByLoginName(loginName);
       return roleList;
    }

    @Override
    public List<User> getUserList(UserSearchFilter filter, Pagination pagination) {
        List<User> userList = userDao.getUserList(filter,pagination);
        return userList;
    }

    @Override
    public int countUserList(UserSearchFilter filter) {
        int count = userDao.countUserList(filter);
        return count;
    }


}
