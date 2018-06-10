package wsylp.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import wsylp.filter.Pagination;
import wsylp.filter.UserSearchFilter;
import wsylp.po.Role;
import wsylp.po.User;

public interface UserMapper extends BaseMapper<User> {


    User getUserLogin(@Param("loginName") String loginName, @Param("password") String password);

    List<User> getUserList();

    List<User> getUserListByFP(@Param("filter") UserSearchFilter filter, @Param("pagination") Pagination pagination);

    long countUserListByFP(@Param("filter") UserSearchFilter filter);

    
}