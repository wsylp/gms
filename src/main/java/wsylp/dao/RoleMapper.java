package wsylp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import wsylp.po.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> getRolesByLoginName(@Param("loginName") String loginName);
}