package wsylp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;



public interface ActivitiService {

    /**
     * 创建表以及数据库
     * 
     * @return
     */
    boolean createtableByXml();

    /**
     * 发布流程
     * 
     * @param folderPath
     *            文件夹地址
     * @param map
     *            参数
     * @return
     */

    boolean devlopActiviti(String folderPath, HashMap<String, String> map);

    /**
     * 发布流程格式为zip
     * 
     * @param folderPath
     *            文件夹地址
     * @param map
     *            参数
     * @return
     */
    boolean deployActivitiByZip(String folderPath, HashMap<String, String> map);

    /**
     * 开始流程并完成当前任务 不建议使用（完成使有风险）
     * 
     * @param processDefinitionKey
     *            流程定义key
     * @param map
     *            存放变量，创建人，机构等信息
     * @return
     */
    boolean startActivitiAndFinsh(String processDefinitionKey, HashMap<String, Object> map);

    /**
     * 开始流程并完成当前任务
     * 
     * @param processDefinitionKey
     *            流程定义key
     * @param map
     *            存放变量，创建人，机构等信息
     * @return
     */
    boolean startActiviti(String processDefinitionKey, HashMap<String, Object> map);

    /**
     * 完成任务
     * 
     * @param taskId
     *            任务id
     * @return
     */
    boolean finshTask(String taskId);

    boolean finshTask(String taskId, Map<String, Object> map);

    /**
     * 获取所有任务
     * 
     * @return
     */
    boolean getTasks();

    /**
     * 根据登录号查询任务
     * 
     * @param processDefinitionKey
     *            流程定义key
     * @param loginName
     *            登录号
     * @return
     */
    boolean getTaskByLoginName(String processDefinitionKey, String loginName);

    /**
     * 查询任务
     * 
     * @param deploymentId
     *            流程部署id
     * @param processDefinitionKey
     *            流程定义key
     * @param loginName
     *            登录号
     * @return
     */
    List<Task> getTaskByDeploymentId(String deploymentId, String processDefinitionKey, String executionId,
                                     String loginName);

    /**
     * 查询流程定义信息
     * 
     * @param processDefinitionKey
     * @return
     */
    boolean getProcessDefin(String processDefinitionKey);

    boolean getProcessImage(String deploymentId);

    /**
     * Deletes the given deployment and cascade deletion to process instances,
     * history process instances and jobs.
     * 
     * @param deploymentId
     * @param cascade
     * @return
     * @see 如果有流程在启动的话，cascade 如果为false就会抛出异常
     */
    boolean deleteDeployment(String deploymentId, boolean cascade);

    /**
     * 默认只删除部署表
     * 
     * @param deploymentId
     * @return
     */
    boolean deleteDeployment(String deploymentId);

    /**
     * 根据执行对象设置变量
     * 
     * @param excutionId
     * @param map
     * @return
     */
    boolean setVariableByExcutionId(String excutionId, boolean isLocal, HashMap<String, Object> map);

    boolean setVariableByTaskId(String taskId, boolean isLocal, HashMap<String, Object> map);

    Map<String, Object> getVariableByExcutionId(String excutionId, boolean isLocal);

    Map<String, Object> getVariableByTaskId(String taskId, boolean isLocal);

    boolean setVariableByTaskId(String taskId, String objectName, Object object);

    <T> T getVariableByTaskId(String taskId, String objectName, Class<T> tClass);

    void getProcessInstanceStateByProcessInstanceId(String processInstanceId);

    void getHistoryProcinst();

    void getHistoryTaskByProcessInstanceId(String processInstanceId);

    /**
     * 驳回流程
     */
    void turnBackNew(String taskId);

    /**
     * 委派任务
     * 
     * @param taskId
     * @param loginName
     * @return
     */
    boolean delegateTask(String taskId, String loginName);

    /**
     * 根据指定人查询任务
     * 
     * @param processDefinitionKey
     * @param assignee
     * @return
     */
    boolean getTaskByAssignee(String processDefinitionKey, String assignee);

    /**
     * 根据任务拥有者查询任务
     * 
     * @param processDefinitionKey
     * @param owner
     * @return
     */
    boolean getTaskByOwner(String processDefinitionKey, String owner);

    /**
     * 委派人完成任务
     * 
     * @param taskId
     * @param map
     * @return
     */
    boolean resolveTask(String taskId, Map<String, Object> map);

    /**
     * 将任务委派他人完成
     * @param taskId
     * @param assignee
     * @return
     */
    boolean trunTask(String taskId, String assignee);
}
