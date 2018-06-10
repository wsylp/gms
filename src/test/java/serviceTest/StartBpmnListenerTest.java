package serviceTest;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wsylp.service.ActivitiService;

/**
 * 监听流程引擎是否启动成功
 * 
 * @author wsylp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml" })
// @ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class StartBpmnListenerTest {

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void createtableByXmlTest() {
        activitiService.createtableByXml();
    }

    @Test
    public void deplomentActiviti() {
        String folderPath = "study/activiti/diagrams/listener";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "流程开始与结束监听器");// 名称
        map.put("id", "listenerTask");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);
    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "listenerApply";
        String orgCode = "070067801";
        // 设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("createLoginName", "0003");
        map.put("orgCode", orgCode);
        activitiService.startActivitiAndFinsh(processDefinitionKey, map);
        // activitiService.startActiviti(processDefinitionKey, map);
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

    public void beforeInit() {
        // ProcessEngineConfigurationImpl processEngineConfiguration) {
        // DataSource dataSource = JdbcUtils.getReadDataSource();
        // processEngineConfiguration.setDataSource(dataSource);
        // processEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);
        // processEngineConfiguration.setDbIdentityUsed(false);
        // databaseSchemaUpdate
        // processEngineConfiguration.setDatabaseSchemaUpdate("true");
        // processEngineConfiguration.setProcessEngineLifecycleListener(myProcessEngineConfigurator);
    }

    @Test
    public void finshTask() {
        String taskId = "2513";
        activitiService.finshTask(taskId);
    }

    @Test
    public void deleteDeploment() {
        String deploymentId = "117501";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId, cascade);
        // activitiService.deleteDeployment(deploymentId);
    }

    @Test
    public void getProcessInstanceStateByProcessInstanceId() {
        String processInstanceId = "75001";
        activitiService.getProcessInstanceStateByProcessInstanceId(processInstanceId);
    }

}
