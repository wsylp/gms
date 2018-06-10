package wsylp.plugins;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyExecutionListener implements ExecutionListener, TaskListener {
    private static final long serialVersionUID = -4402023685783762756L;

    private static final Logger LOGGER = LogManager.getLogger(MyExecutionListener.class.getName());

    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        if ("start".equals(eventName)) {
            LOGGER.info("start=========");
        } else if ("end".equals(eventName)) {
            LOGGER.info("end=========");
        } else if ("take".equals(eventName)) {
            LOGGER.info("take=========");
        }
    }

    // 实现TaskListener中的方法
    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        if ("create".endsWith(eventName)) {
            LOGGER.info("create=========");
        } else if ("assignment".endsWith(eventName)) {
            LOGGER.info("assignment========");
        } else if ("complete".endsWith(eventName)) {
            LOGGER.info("complete===========");
        } else if ("delete".endsWith(eventName)) {
            LOGGER.info("delete=============");
        }

    }
}
