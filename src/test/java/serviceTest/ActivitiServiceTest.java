package serviceTest;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wsylp.service.ActivitiService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml" })
 @ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class ActivitiServiceTest {

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void createtableByXmlTest() {
        activitiService.createtableByXml();
    }

    @Test
    public void deplomentActiviti() {
        String folderPath = "study/activiti/diagrams/leaveBill";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "请假流程");// 名称
        map.put("id", "leaveBill");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);
    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "leaveBill";
        String orgCode = "070067801";
        // 设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("createLoginName", "0003");
        map.put("orgCode", orgCode);
        activitiService.startActivitiAndFinsh(processDefinitionKey, map);

    }

    @Test
    public void getTaskByloginName() {
        String loginName = "0002";
        String processDefinitionKey = "leaveBill";
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
        String loginName = "zjl";// 总经理
        // activitiService.getTaskByDeploymentId(deploymentId, processDefinitionKey,
        // loginName);
    }

    @Test
    public void finshTask() {
        String taskId = "2513";
        activitiService.finshTask(taskId);
    }

    @Test
    public void getProcessDefin() {
        String processDefinitionKey = "leaveBill";
        activitiService.getProcessDefin(processDefinitionKey);
    }

    @Test
    public void getProcessImage() {
        String deploymentId = "1";
        activitiService.getProcessImage(deploymentId);

    }

    @Test
    public void deleteDeploment() {
        String deploymentId = "72501";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId, cascade);
        // activitiService.deleteDeployment(deploymentId);
    }

    @Test
    public void getProcessInstanceStateByProcessInstanceId() {
        String processInstanceId = "70001";
        activitiService.getProcessInstanceStateByProcessInstanceId(processInstanceId);
    }

    @Test
    public void getHistoryProcinst() {
        activitiService.getHistoryProcinst();
    }

    @Test
    public void getHistoryTaskByProcessInstanceId() {
        String processInstanceId = "75001";
        activitiService.getHistoryTaskByProcessInstanceId(processInstanceId);
    }

    @Test
    public void turnBackNew() {
        String taskId = "82513";
        activitiService.turnBackNew(taskId);
    }
    
}
