package wsylp.service;

import java.util.List;

import wsylp.po.Role;
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
}
