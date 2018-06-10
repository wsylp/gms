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
@ContextConfiguration(locations = {"classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml"})
// @ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class DelegateTaskTest {

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void createtableByXmlTest() {
        activitiService.createtableByXml();
    }

    @Test
    public void deplomentActiviti() {
        String folderPath = "study/activiti/diagrams/delegate";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "委派任务");// 名称
        map.put("id", "delegateTask");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);
    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "delegateTask";
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
        // activitiService.getTaskByDeploymentId(deploymentId,processDefinitionKey,
        // loginName);
    }

    @Test
    public void finshTask() {
        String taskId = "70011";
        activitiService.finshTask(taskId);
    }

    @Test
    public void deleteDeploment() {
        String deploymentId = "55001";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId, cascade);
        // activitiService.deleteDeployment(deploymentId);
    }

    @Test
    public void getHistoryTaskByProcessInstanceId() {
        String processInstanceId = "75001";
        activitiService.getHistoryTaskByProcessInstanceId(processInstanceId);
    }

    /**
     * 指定代办人
     */
    @Test
    public void delegateTask() {
        String taskId = "2511";
        String loginName = "0003";
        activitiService.delegateTask(taskId, loginName);
    }

    @Test
    public void getTaskByAssignee() {
        String processDefinitionKey = "delegateTask";
        String assignee = "0003";
        activitiService.getTaskByAssignee(processDefinitionKey, assignee);
    }

    /**
     * 正在运行的任务表中被委派人办理任务后任务会回到委派人 ，历史任务表中也一样,只是多了一个人进行审批
     */
    @Test
    public void resolveTask() {
        String taskId = "2511";
        Map<String, Object> map = new HashMap<>();
        activitiService.resolveTask(taskId, map);
    }

    @Test
    public void getTaskByOwner() {
        String processDefinitionKey = "delegateTask";
        String owner = "0003";
        activitiService.getTaskByOwner(processDefinitionKey, owner);
    }

    /**
     * 任务转办,将任务交给其他人处理
     */
    @Test
    public void turnTask() {
        String taskId = "7511";
        String assignee = "0003";
        activitiService.trunTask(taskId, assignee);
    }

    /**
     * 挂起流程
     */
    @Test
    public void suspendProcess() {
        //SuspensionState ACTIVE = new SuspensionStateImpl(1, "active"); 激活
        //  SuspensionState SUSPENDED = new SuspensionStateImpl(2, "suspended"); 挂起
        String processInstanceId = "70001";
        activitiService.suspendProcessInstanceById(processInstanceId);
    }

    @Test
    public void deleteProcessInstance() {
        String processInstanceId = "70001";
        String reason = "删除原因";
        activitiService.deleteProcessInstance(processInstanceId, reason);
    }


    @Test
    public void activateProcessInstanceById(){
        String processInstanceId = "70001";
        activitiService.activateProcessInstanceById(processInstanceId);
    }

    @Test
    public void suspendProcessDefineBykey(){
        String processDefineKey = "";
        boolean cascade = true;
        activitiService.suspendProcessDefineKey(processDefineKey,cascade);
    }
}
