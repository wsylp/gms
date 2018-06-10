package wsylp.plugins;

import java.util.Iterator;
import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class JDJumpTaskCmd implements Command<Void> {
    protected String executionId;
    protected ActivityImpl desActivity;
    protected Map<String, Object> paramvar;
    protected ActivityImpl currentActivity;

    /**
     * 分享牛原创(尊重原创 转载对的时候第一行请注明，转载出处来自分享牛http://blog.csdn.net/qq_30739519)
     */
    public Void execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = Context.getCommandContext().getExecutionEntityManager();
        // 获取当前流程的executionId，因为在并发的情况下executionId是唯一的。
        ExecutionEntity executionEntity = executionEntityManager.findExecutionById(executionId);
        executionEntity.setVariables(paramvar);
        executionEntity.setEventSource(this.currentActivity);
        executionEntity.setActivity(this.currentActivity);
        // 根据executionId 获取Task
        Iterator<TaskEntity> localIterator = Context.getCommandContext().getTaskEntityManager()
                .findTasksByExecutionId(this.executionId).iterator();

        while (localIterator.hasNext()) {
            TaskEntity taskEntity = (TaskEntity) localIterator.next();
            // 触发任务监听
            taskEntity.fireEvent("complete");
            // 删除任务的原因
            Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, "completed", false);
        }
        executionEntity.executeActivity(this.desActivity);
        return null;
    }

    /**
     * 构造参数 可以根据自己的业务需要添加更多的字段 分享牛原创(尊重原创
     * 转载对的时候第一行请注明，转载出处来自分享牛http://blog.csdn.net/qq_30739519)
     * 
     * @param executionId
     * @param desActivity
     * @param paramvar
     * @param currentActivity
     */
    public JDJumpTaskCmd(String executionId, ActivityImpl desActivity, Map<String, Object> paramvar,
            ActivityImpl currentActivity) {
        this.executionId = executionId;
        this.desActivity = desActivity;
        this.paramvar = paramvar;
        this.currentActivity = currentActivity;
    }
}
