package serviceTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wsylp.plugins.JDJumpTaskCmd;
import wsylp.service.ActivitiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml" })
// @ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class JumpServiceTest {

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private TaskService taskService;

    @Test
    public void createtableByXmlTest() {
        activitiService.createtableByXml();
    }

    @Test
    public void deplomentActiviti() {
        String folderPath = "study/activiti/diagrams/jumpTask";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "任务跳动");// 名称
        map.put("id", "jumpTask");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);
    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "jumpTask";
        String orgCode = "070067801";
        // 设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("createLoginName", "0003");
        map.put("orgCode", orgCode);
        activitiService.startActiviti(processDefinitionKey, map);

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
        String deploymentId = "87501";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId, cascade);
        // activitiService.deleteDeployment(deploymentId);
    }

    @Test
    public void getProcessInstanceStateByProcessInstanceId() {
        String processInstanceId = "75001";
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

    @Test
    public void jumpTask1() {
        Map<String, Object> vars = new HashMap<String, Object>();
        String[] v = { "shareniu1", "shareniu2", "shareniu3", "shareniu4" };
        vars.put("assigneeList", Arrays.asList(v));
        // 分享牛原创(尊重原创 转载对的时候第一行请注明，转载出处来自分享牛http://blog.csdn.net/qq_30739519)
        ReadOnlyProcessDefinition processDefinitionEntity = (ReadOnlyProcessDefinition) repositoryService
                .getProcessDefinition("jumpTask:1:92504");
        // 目标节点
        ActivityImpl destinationActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask5");
        String executionId = "95001";
        // 当前节点
        ActivityImpl currentActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask3");
        managementService.executeCommand(new JDJumpTaskCmd(executionId, destinationActivity, vars, currentActivity));
    }

    @Test
    public void jumpTask2() {
        Map<String, Object> vars = new HashMap<String, Object>();
        String[] v = { "shareniu1", "shareniu2", "shareniu3", "shareniu4" };
        vars.put("assigneeList", Arrays.asList(v));
        // 分享牛原创(尊重原创 转载对的时候第一行请注明，转载出处来自分享牛http://blog.csdn.net/qq_30739519)
        CommandExecutor commandExecutor = ((TaskServiceImpl) taskService).getCommandExecutor();
        String executionId = "";
        ActivityImpl destinationActivity = null;
        ActivityImpl currentActivity = null;
        commandExecutor.execute(new JDJumpTaskCmd(executionId, destinationActivity, vars, currentActivity));
    }

}
