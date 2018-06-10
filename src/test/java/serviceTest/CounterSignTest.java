package serviceTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.fabric.xmlrpc.base.Array;

import wsylp.service.ActivitiService;

/**
 * isSequential 指定多实例是按照并行或者串行的方式进行，
 * 如果使用串行方式nrOfActiveInstances 变量始终是1 。
 * isSequential串行并行是针对于assigneeList中的用户集合来说的，
 * 如果是并行assigneeList集合中的每个用户按照顺序执行。
 * 任务会签处理 一个任务需要多热进行处理
 * 
 * 
 * @author wsylp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:gms/spring-m*.xml", "classpath*:gms/spring-a*.xml" })
// @ContextConfiguration(locations = { "classpath*:gms/spring-*.xml" })
public class CounterSignTest {

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void createtableByXmlTest() {
        activitiService.createtableByXml();
    }

    @Test
    public void deplomentActiviti() {
        String folderPath = "study/activiti/diagrams/counterSign";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "会签流程");// 名称
        map.put("id", "counterSign");// id
        map.put("category", "办公流程");// 类别
        activitiService.devlopActiviti(folderPath, map);
    }

    @Test
    public void startActiviti() {
        String processDefinitionKey = "counterSign";
        String orgCode = "070067801";
        String[] assigneeList = {"0001","0002","0003"};
        // 设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("createLoginName", "0003");
        map.put("orgCode", orgCode);
      //必须是List
        map.put("assigneeList", Arrays.asList(assigneeList));
        //map.put("signCount", 0);
        activitiService.startActivitiAndFinsh(processDefinitionKey, map);

    }

    @Test
    public void getTaskByloginName() {
        String loginName = "0002";
        String processDefinitionKey = "counterSign";
        // 任务处理人【0003】流程名称【采购申请】任务id【10006】流程定义id【buyBill:2:2504】
        //activitiService.getTaskByLoginName(processDefinitionKey, loginName);
        activitiService.getTaskByAssignee(processDefinitionKey, loginName);
    }

    @Test
    public void getTasks() {
        activitiService.getTasks();
    }

    @Test
    public void getTaskByDeploymentId() {
        //任务处理人【0001】,任务id【42504】,任务名称【总经理审批】,任务流程定义id【counterSign:1:32504】, 流程定义key【zjlsp】,任务拥有者【null】 
        String deploymentId = "1";
        String processDefinitionKey = "leaveBill";
        String loginName = "zjl";// 总经理
        // activitiService.getTaskByDeploymentId(deploymentId, processDefinitionKey,
        // loginName);
    }

    @Test
    public void finshTask() {
        //57530
        // 57537
        //  57544
        String taskId = "62504";//35030 //35037 //35044
        activitiService.finshTask(taskId);
    }

    @Test
    public void deleteDeploment() {
        String deploymentId = "45001";
        boolean cascade = true;
        activitiService.deleteDeployment(deploymentId, cascade);
        // activitiService.deleteDeployment(deploymentId);
    }
    
    @Test
    public void getValaier() {
        String taskId = "35044";
        Map<String, Object> variable = activitiService.getVariableByTaskId(taskId , false);
        System.out.println("nrOfInstances 实例总数："+variable.get("nrOfInstances"));
        System.out.println("nrOfActiveInstances  当前还没有完成的实例个数："+variable.get("nrOfActiveInstances"));
        System.out.println("nrOfCompletedInstances 当前完成的实例个数数：："+variable.get("nrOfCompletedInstances"));
    }

}
