package wsylp.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wsylp.filter.Pagination;
import wsylp.po.Role;
import wsylp.service.ActivitiService;
import wsylp.service.UserService;

@Service("activitiService")
public class ActivitiServiceImpl implements ActivitiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public boolean createtableByXml() {
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("gms/spring-activiti.xml");
        // .createProcessEngineConfigurationFromResourceDefault();
        engineConfiguration.buildProcessEngine();
        LOGGER.info("创建成功");
        return true;
    }

    @Override
    public boolean devlopActiviti(String folderPath, HashMap<String, String> map) {
        Deployment deploy = repositoryService.createDeployment()// 创建一个部署构建器
                .addClasspathResource(folderPath + File.separator + map.get("id") + ".bpmn")// 从类路径一次只能添加一个文件
                .addClasspathResource(folderPath + File.separator + map.get("id") + ".png")// 流程图片
                .name(map.get("name")).category(map.get("category")).deploy();

        LOGGER.info("流程名称【 {}】", deploy.getName());
        LOGGER.info("流程id【{}】", deploy.getId());
        LOGGER.info("流程类别【{}】", deploy.getCategory());

        return true;
    }

    @Override
    public boolean deployActivitiByZip(String folderPath, HashMap<String, String> map) {
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("folderPath" + File.separator + map.get("id") + ".bpmn");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deploy = repositoryService.createDeployment()// 创建一个部署构建器
                .addZipInputStream(zipInputStream).name(map.get("name")).category(map.get("category")).deploy();

        LOGGER.info("流程名称【 {}】", deploy.getName());
        LOGGER.info("流程id【{}】", deploy.getId());
        LOGGER.info("流程类别【{}】", deploy.getCategory());
        return true;
    }

    @Override
    public boolean startActiviti(String processDefinitionKey, HashMap<String, Object> map) {
        // 取得流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);

        LOGGER.info("流程(流程执行对象实例)id【{}】", processInstance.getId());// execution对象
        LOGGER.info("流程实例id:【{}】", processInstance.getProcessInstanceId());// processInstance对象
        LOGGER.info("流程定义id【{}】", processInstance.getProcessDefinitionId());// 默认为最新的id
        return true;
    }

    @Override
    public boolean startActivitiAndFinsh(String processDefinitionKey, HashMap<String, Object> map) {
        // 取得流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);

        LOGGER.info("流程(流程执行对象实例)id【{}】", processInstance.getId());// execution对象
        LOGGER.info("流程实例id:【{}】", processInstance.getProcessInstanceId());// processInstance对象
        LOGGER.info("流程定义id【{}】", processInstance.getProcessDefinitionId());// 默认为最新的id
        LOGGER.info("流程实例id【{}】", processInstance.getSuperExecutionId());
        List<Task> tasks = this.getTaskByDeploymentId(processInstance.getDeploymentId(), processDefinitionKey,
                processInstance.getId(), (String) map.get("createLoginName"));

        for (Task task : tasks) {
            this.finshTask(task.getId());
        }

        return true;
    }

    @Override
    public boolean finshTask(String taskId) {
        taskService.complete(taskId);
        LOGGER.info("完成任务【{}】", taskId);
        return true;
    }

    @Override
    public boolean finshTask(String taskId, Map<String, Object> map) {
        taskService.complete(taskId, map);
        LOGGER.info("完成任务【{}】", taskId);
        return true;
    }

    @Override
    public boolean getTasks() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 任务列表
        List<Task> list = taskQuery.list();

        for (Task task : list) {
            LOGGER.info("任务处理人【{}】,任务id【{}】,任务名称【{}】,任务流程定义id【{}】, 流程定义key【{}】,任务拥有者【{}】 ", task.getAssignee(),
                    task.getId(), task.getName(), task.getProcessDefinitionId(), task.getTaskDefinitionKey(),
                    task.getOwner());
        }
        return true;
    }

    @Override
    public boolean getTaskByLoginName(String processDefinitionKey, String loginName) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 查询登录人所在角色，在根据角色进行查询
        List<Role> roleList = userService.getRolesByLoginName(loginName);
        for (Role role : roleList) {
            // 任务列表
            List<Task> list = taskQuery.processDefinitionKey(processDefinitionKey).taskAssignee(role.getRoleCode())
                    // . taskCandidateUser(assignee)
                    .list();// 组任务的办理人查询

            for (Task task : list) {
                LOGGER.info("任务处理人【{}】", task.getAssignee());
                LOGGER.info("流程名称【{}】", task.getName());
                LOGGER.info("任务id【{}】", task.getId());
                LOGGER.info("流程定义id【{}】", task.getProcessDefinitionId());
                LOGGER.info("执行对象id【{}】", task.getExecutionId());
            }

        }

        return true;
    }

    @Override
    public List<Task> getTaskByDeploymentId(String deploymentId, String processDefinitionKey, String executionId,
            String loginName) {
        List<Task> list = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey)
                .deploymentId(deploymentId).taskAssignee(loginName).executionId(executionId).list();
        // 获取当前人的
        return list;
    }

    @Override
    public boolean getProcessDefin(String processDefinitionKey) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                // .processDefinitionId(processDefinitionId)//流程定义id
                // BuyBill:1:10004 组成，proDefiKey(流程定义的key) + version(版本) + 自动生成的id
                .processDefinitionKey(processDefinitionKey)// 流程定义的key
                // .processDefinitionName(processDefinitionName)//流程定义名称
                // .processDefinitionVersion(processDefinitionVersion)//流程定义版本
                // .latestVersion()//最新版本
                // .orderByProcessDefinitionName().desc()//安装版本降序排序
                // .count()//统计结果
                // .listPage(firstResult, maxResults)//分页查询
                .list();
        // 遍历结果
        for (ProcessDefinition processDefinition : list) {
            LOGGER.info("流程定义id【{}】", processDefinition.getId());
            LOGGER.info("流程定义的key【{}】", processDefinition.getKey());
            LOGGER.info("流程部署id【{}】", processDefinition.getDeploymentId());
            LOGGER.info("流程定义的版本【{}】", processDefinition.getVersion());
            LOGGER.info("流程资源名称【{}】", processDefinition.getResourceName());
        }
        return true;
    }

    @Override
    public boolean getProcessImage(String deploymentId) {
        List<String> resourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
        for (String resourceName : resourceNames) {
            if (resourceName.endsWith(".png")) {
                LOGGER.info("流程资源名称【{}】", resourceName);

                /**
                 * 读取资源
                 * 
                 * @params deploymentId 部署id
                 * @params resourceName 资源文件名
                 */
                try {
                    InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
                    // 把流写入到文件中
                    String pathName = "E:/" + resourceName;
                    File file = new File(pathName);
                    FileUtils.copyInputStreamToFile(resourceAsStream, file);
                    LOGGER.info("输出完成");
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteDeployment(String deploymentId, boolean cascade) {
        repositoryService.deleteDeployment(deploymentId, cascade);
        return true;
    }

    @Override
    public boolean deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
        return true;
    }

    @Override
    public boolean setVariableByExcutionId(String executionId, boolean isLocal, HashMap<String, Object> map) {
        /*
         * runtimeService.setVariablesLocal(executionId, variableName, value);
         * 
         * 设置本执行对象的变量，该作用域只在当前的executionId中 runtimeService.setVariableLocal(executionId,
         * variableName, value);
         * 
         * 可以设置对个变量，放在map中
         */
        if (isLocal) {
            runtimeService.setVariablesLocal(executionId, map);
        } else {
            runtimeService.setVariables(executionId, map);
        }
        return true;
    }

    @Override
    public boolean setVariableByTaskId(String taskId, boolean isLocal, HashMap<String, Object> map) {
        /*
         * taskService.setVariable(taskId, variableName, value);
         *
         * 设置本执行对象的变量，该作用域只在当前的executionId中 taskService.setVariableLocal(taskId,
         * variableName, value); 可以设置对个变量，放在map中
         */
        if (isLocal) {
            taskService.setVariablesLocal(taskId, map);
        } else {
            taskService.setVariables(taskId, map);
        }
        return true;
    }

    @Override
    public Map<String, Object> getVariableByExcutionId(String executionId, boolean isLocal) {
        Map<String, Object> variablesMap = new HashMap<String, Object>();
        if (isLocal) {
            variablesMap = runtimeService.getVariablesLocal(executionId);
        } else {
            variablesMap = runtimeService.getVariables(executionId);
        }
        return variablesMap;
    }

    @Override
    public Map<String, Object> getVariableByTaskId(String taskId, boolean isLocal) {
        Map<String, Object> variablesMap = new HashMap<String, Object>();
        if (isLocal) {
            variablesMap = taskService.getVariablesLocal(taskId);
        } else {
            variablesMap = taskService.getVariables(taskId);
        }
        return variablesMap;
    }

    @Override
    public boolean setVariableByTaskId(String taskId, String objectName, Object object) {
        taskService.setVariable(taskId, objectName, object);
        return true;
    }

    @Override
    public <T> T getVariableByTaskId(String taskId, String objectName, Class<T> tClass) {
        T bean = (T) taskService.getVariable(taskId, objectName);
        return bean;
    }

    @Override
    public void getProcessInstanceStateByProcessInstanceId(String processInstanceId) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();// 返回数据结果，要么单行，要么是空，其他情况报错;

        if (pi != null) {
            LOGGER.info("该流程实例【{}】正在运行.....", processInstanceId);
            LOGGER.info("当前活动的任务【{}】,名称为", pi.getActivityId());
        } else {
            LOGGER.info("该任务已经结束。。。");
        }

    }

    @Override
    public void getHistoryProcinst() {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
        if (list != null && list.size() > 0) {
            for (HistoricProcessInstance m : list) {
                LOGGER.info("历史的流程实例【{}】", m.getId());
                LOGGER.info("历史流程定义id【{}】", m.getProcessDefinitionId());
                LOGGER.info("历史流程实例开始时间{}----结束时间:{}--->", m.getStartTime(), m.getEndTime());
            }
        }

    }

    @Override
    public void getHistoryTaskByProcessInstanceId(String processInstanceId) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).list();

        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance m : list) {
                LOGGER.info("历史的流程任务【{}】", m.getId());
                LOGGER.info("历史流程定义id【{}】", m.getProcessDefinitionId());
                LOGGER.info("历史任务名称【{}】", m.getName());
                LOGGER.info("历史任务实例处理人【{}】", m.getAssignee());
            }
        }

    }

    @Override
    public void turnBackNew(String taskId) {
        try {
            Map<String, Object> variables;
            // 取得当前任务
            HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
                    .singleResult();
            // 取得流程实例
            ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(currTask.getProcessInstanceId()).singleResult();
            if (instance == null) {
                // 流程已经结束
            }
            variables = instance.getProcessVariables();
            // 取得流程定义
            ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                    .getDeployedProcessDefinition(currTask.getProcessDefinitionId());
            if (definition == null) {
                // 流程定义未找到
            }
            // 取得上一步活动
            ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                    .findActivity(currTask.getTaskDefinitionKey());
            List<PvmTransition> nextTransitionList = currActivity.getIncomingTransitions();
            // 清除当前活动的出口
            List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
            List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitionList) {
                oriPvmTransitionList.add(pvmTransition);
            }
            pvmTransitionList.clear();

            // 建立新出口
            List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
            for (PvmTransition nextTransition : nextTransitionList) {
                PvmActivity nextActivity = nextTransition.getSource();
                ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition).findActivity(nextActivity.getId());
                TransitionImpl newTransition = currActivity.createOutgoingTransition();
                newTransition.setDestination(nextActivityImpl);
                newTransitions.add(newTransition);
            }
            // 完成任务
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId())
                    .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
            for (Task task : tasks) {
                taskService.complete(task.getId(), variables);
                historyService.deleteHistoricTaskInstance(task.getId());
            }
            // 恢复方向
            for (TransitionImpl transitionImpl : newTransitions) {
                currActivity.getOutgoingTransitions().remove(transitionImpl);
            }
            for (PvmTransition pvmTransition : oriPvmTransitionList) {
                pvmTransitionList.add(pvmTransition);
            }

            // 成功
            LOGGER.info("驳回成功");
        } catch (Exception e) {
            // 异常
        }
    }

    @Override
    public boolean delegateTask(String taskId, String loginName) {
        taskService.delegateTask(taskId, loginName);
        return true;
    }

    @Override
    public boolean getTaskByAssignee(String processDefinitionKey, String assignee) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 任务列表
        List<Task> list = taskQuery.processDefinitionKey(processDefinitionKey).taskAssignee(assignee).list();// 指定办理人
        for (Task task : list) {
            LOGGER.info("任务处理人【{}】", task.getAssignee());
            LOGGER.info("流程名称【{}】", task.getName());
            LOGGER.info("任务id【{}】", task.getId());
            LOGGER.info("流程定义id【{}】", task.getProcessDefinitionId());
            LOGGER.info("执行对象id【{}】", task.getExecutionId());
        }
        return true;
    }

    @Override
    public boolean getTaskByOwner(String processDefinitionKey, String owner) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 任务列表
        List<Task> list = taskQuery.processDefinitionKey(processDefinitionKey).taskOwner(owner).list();// 指定办理人
        for (Task task : list) {
            LOGGER.info("任务处理人【{}】", task.getAssignee());
            LOGGER.info("流程名称【{}】", task.getName());
            LOGGER.info("任务id【{}】", task.getId());
            LOGGER.info("流程定义id【{}】", task.getProcessDefinitionId());
            LOGGER.info("执行对象id【{}】", task.getExecutionId());
            LOGGER.info("任务拥有者【{}】", task.getOwner());
        }
        return true;
    }

    @Override
    public boolean resolveTask(String taskId, Map<String, Object> map) {
        taskService.resolveTask(taskId, map);
        return true;
    }

    @Override
    public boolean trunTask(String taskId, String assignee) {
        taskService.setAssignee(taskId, assignee);
        return true;
    }

    @Override
    public boolean suspendProcessInstanceById(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return true;
    }

    @Override
    public boolean deleteProcessInstance(String processInstanceId, String reason) {
        runtimeService.deleteProcessInstance(processInstanceId, reason);
        return true;
    }

    @Override
    public boolean activateProcessInstanceById(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
        return true;
    }

    @Override
    public boolean suspendProcessDefineKey(String processDefineKey, boolean cascade) {
        repositoryService.suspendProcessDefinitionByKey(processDefineKey,cascade,new Date());
        return true;
    }

    @Override
    public boolean activateProcessDefinitionByKey(String processDefineKey,boolean cascade){
        repositoryService.activateProcessDefinitionByKey(processDefineKey,cascade,new Date());
        return true;

    }
}
