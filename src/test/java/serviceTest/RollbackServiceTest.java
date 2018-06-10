package serviceTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wsylp.service.ActivitiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml" })
// @ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class RollbackServiceTest {

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void createtableByXmlTest() {
        activitiService.createtableByXml();
    }

    @Test
    public void deplomentActiviti() {
        String folderPath = "study/activiti/diagrams/rollback";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "申请驳回流程");// 名称
        map.put("id", "rollback");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);
    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "rollback";
        String orgCode = "070067801";
        // 设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("createLoginName", "0003");
        map.put("orgCode", orgCode);
        activitiService.startActivitiAndFinsh(processDefinitionKey, map);
        //activitiService.startActiviti(processDefinitionKey, map);

    }

    @Test
    public void getTaskByloginName() {
        String loginName = "0002";
        String processDefinitionKey = "rollback";
        // 任务处理人【0003】流程名称【采购申请】任务id【10006】流程定义id【buyBill:2:2504】
        activitiService.getTaskByLoginName(processDefinitionKey, loginName);
    }

    @Test
    public void getTasks() {
        activitiService.getTasks();
    }

    @Test
    public void getTaskByDeploymentId() {
        String deploymentId = "1";
        String processDefinitionKey = "leaveBill";
        String loginName = "zjl";//总经理
        String executionId = "";
        activitiService.getTaskByDeploymentId(deploymentId, processDefinitionKey,executionId, loginName);
    }

    
    @Test
    public void finshTask() {
        String taskId = "57511";
        Map<String,Object> map = new HashMap<>();
        map.put("advice", "reject");
        activitiService.finshTask(taskId,map );
    }
    
    @Test
    public void getProcessDefin() {
        String processDefinitionKey = "leaveBill";
        activitiService.getProcessDefin(processDefinitionKey );
    }
    
    
    @Test
    public void getProcessImage() {
        String  deploymentId = "1";
        activitiService.getProcessImage(deploymentId );
        
    }
    
    
    @Test
    public void deleteDeploment() {
        String deploymentId = "42501";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId,cascade );
       // activitiService.deleteDeployment(deploymentId);
    }
    
}
