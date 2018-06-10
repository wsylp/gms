package serviceTest;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wsylp.service.ActivitiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml" })
// @ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class GatewayServiceTest {

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void createtableByXmlTest() {
        activitiService.createtableByXml();
    }

    @Test
    public void deplomentActiviti() {
        String folderPath = "study/activiti/diagrams/gateway";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "网关流程");// 名称
        map.put("id", "gateway");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);
    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "gateway";
        String orgCode = "070067801";
        // 设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("createLoginName", "0003");
        map.put("orgCode", orgCode);
        map.put("visitor", "普通");
        activitiService.startActivitiAndFinsh(processDefinitionKey, map);

    }

    @Test
    public void getTaskByloginName() {
        String loginName = "0004";
        String processDefinitionKey = "gateway";
        // 任务处理人【0003】流程名称【采购申请】任务id【10006】流程定义id【buyBill:2:2504】
        activitiService.getTaskByLoginName(processDefinitionKey, loginName);
    }

    @Test
    public void getTasks() {
        activitiService.getTasks();
    }

    
    @Test
    public void finshTask() {
        String taskId = "2513";
        activitiService.finshTask(taskId );
    }
    
    @Test
    public void getProcessDefin() {
        String processDefinitionKey = "gateway";
        activitiService.getProcessDefin(processDefinitionKey );
    }
    
    
    @Test
    public void getProcessImage() {
        String  deploymentId = "1";
        activitiService.getProcessImage(deploymentId );
        
    }
    
    
    @Test
    public void deleteDeploment() {
        String deploymentId = "55001";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId,cascade );
       // activitiService.deleteDeployment(deploymentId);
    }
    
}
