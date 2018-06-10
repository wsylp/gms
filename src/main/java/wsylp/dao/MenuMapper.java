package wsylp.dao;

import java.util.List;

import wsylp.po.Menu;

public interface MenuMapper  extends BaseMapper<Menu> {

    List<Menu> getMenus();


    List<Menu> getMenuByParentId(String parentId);
}