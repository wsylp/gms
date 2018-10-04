package wsylp.service;

import java.util.List;

import wsylp.filter.Pagination;
import wsylp.filter.UserSearchFilter;
import wsylp.po.Role;
import wsylp.po.User;
import wsylp.vo.MenuTree;

/**
 * Created by wsylp on 2017/5/4.
 */

public interface UserService {

    List<MenuTree> getMenuTree();

    /**
     * 根据登录号查询角色
     * @param loginName
     * @return
     */
    List<Role> getRolesByLoginName(String loginName);

    /**
     * 查询所有用户
     * @param filter
     * @param pagination
     * @return
     */
    List<User> getUserList(UserSearchFilter filter, Pagination pagination);

    /**
     * 查询用户数目
     *
     * @param filter
     * @return
     */
    int countUserList(UserSearchFilter filter);
}
