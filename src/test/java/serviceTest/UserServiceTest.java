package serviceTest;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wsylp.po.Role;
import wsylp.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml" })
public class UserServiceTest {
    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class.getName());

    @Autowired
    private UserService userService;

    @Test
    public void getRolesByLoginName() {
        String loginName = "0003";
        List<Role> roles = userService.getRolesByLoginName(loginName);
        logger.info("数目【{}】",roles.size());
        for (Role role : roles) {
            logger.info("角色id{},角色code【{}】,角色名称【{}】", role.getId(), role.getRoleCode(), role.getName());
        }
    }

}
