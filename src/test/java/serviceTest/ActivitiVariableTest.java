package serviceTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wsylp.plugins.SequenceFlow;
import wsylp.service.ActivitiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class ActivitiVariableTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiVariableTest.class);
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ActivitiService activitiService;

    /**
     * setValiable 与 setLocalValiable作用域不同
     */
    @Test
    public void deploeyFlow() {
        String folderPath = "study/activiti/diagrams/sequenceFlow";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "连线流程");// 名称
        map.put("id", "sequenceFlow");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);

    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "sequenceFlow";
        String orgCode = "070067801";
        // 设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("createLoginName", "0003");
        map.put("orgCode", orgCode);
        activitiService.startActiviti(processDefinitionKey, map);

    }

    @Test
    public void getTaskByloginName() {
        String loginName = "0001";
        String processDefinitionKey = "sequenceFlow";
        // 任务处理人【0003】流程名称【采购申请】任务id【10006】流程定义id【buyBill:2:2504】
        activitiService.getTaskByLoginName(processDefinitionKey, loginName);
    }

    @Test
    public void deleteDeploment() {
        String deploymentId = "15001";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId, cascade);
        // activitiService.deleteDeployment(deploymentId);
    }

    /**
     * 1：通过runtimeService来设置流程变量
     * 
     * runtimeService.setVariable(executionId, variableName, value);
     * 
     * // 设置本执行对象的变量，该作用域只在当前的executionId中
     * runtimeService.setVariableLocal(executionId, variableName, value);
     * 
     * Map<String, Object> variables = new HashMap<String, Object>(); //
     * 可以设置对个变量，放在map中 runtimeService.setVariables(executionId, variables);
     * 
     * 2：通过taskService来设置流程变量
     * 
     * String taskId = ""; taskService.setVariable(taskId, variableName, value);
     * 
     * // 设置本执行对象的变量，该作用域只在当前的executionId中 taskService.setVariableLocal(taskId,
     * variableName, value);
     * 
     * Map<String, Object> variableTasks = new HashMap<String, Object>(); //
     * 可以设置对个变量，放在map中 taskService.setVariables(taskId, variableTasks);
     * 
     * 3：当流程开始执行的时候可以设置变量
     *
     * runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
     * 
     * 4:当任务执行的时候设置流程变量
     * 
     * taskService.complete(taskId, variables);
     * 
     * 
     * 5:通过runtimeService取变量值
     * 
     * 
     * runtimeService.getVariable(executionId, variableName);
     * runtimeService.getVariableLocal(executionId, variableName);
     * runtimeService.getVariables(executionId);
     * 
     * 
     * 6:通过taskService 取变量值
     * 
     * taskService.getVariable(taskId, variableName);
     * taskService.getVariableLocal(taskId, variableName);
     * taskService.getVariables(taskId);
     */
    @Test
    public void setVariable() {
        // String excutionId = "25001";
        String taskId = "30004";
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", "重要");
        boolean isLocal = true;
        // activitiService.setVariableByExcutionId(excutionId, isLocal, map);
        activitiService.setVariableByTaskId(taskId, isLocal, map);

    }

    /**
     * 设置流程变量值
     */
    @Test
    public void setVariablesByBean() {
        String taskId = "30004";

        /**
         * 支持的变量类型
         * 
         * <br>
         * 简单类型：String boolean Integer double date <br>
         * 自定义对象bean
         */
        // 传递的是一个自定义的bean对象
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setCreateLoginName("0003");
        sequenceFlow.setDate(new Date());
        sequenceFlow.setMessage("重要");
        activitiService.setVariableByTaskId(taskId, "sequenceFlow", sequenceFlow);

    }

    @Test
    public void getVariable() {
        String excutionId = "25001";
        String taskId = "30004";
        Map<String, Object> map = new HashMap<>();

        boolean isLocal = false;
        // map = activitiService.getVariableByExcutionId(excutionId, isLocal );
        // LOGGER.info("message【{}】",(String)map.get("message"));
        // map = activitiService.getVariableByTaskId(taskId, isLocal);
        SequenceFlow sequenceFlow = activitiService.getVariableByTaskId(taskId, "sequenceFlow", SequenceFlow.class);

        LOGGER.info("创建时间【{}】", sequenceFlow.getDate());
    }

    @Test
    public void finshTask() {
        String taskId = "30004";
        activitiService.finshTask(taskId);
    }

}
