package wsylp.service.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 用于监听流程引擎的启动与关闭
 * 
 * @author wsylp
 *
 */
@Service("myProcessEngineLifecycleListener")
public class MyProcessEngineLifecycleListener implements ProcessEngineLifecycleListener   {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiServiceImpl.class);

    @Override
    public void onProcessEngineBuilt(ProcessEngine processEngine) {
        LOGGER.debug("processEngine:onProcessEngineBuilt{}开始启动", processEngine);
        LOGGER.info("processEngine:onProcessEngineBuilt:{}开始启动" + processEngine);
    }

    @Override
    public void onProcessEngineClosed(ProcessEngine processEngine) {
        LOGGER.debug("processEngine:onProcessEngineClosed{}结束流程", processEngine);
        LOGGER.info("processEngine:onProcessEngineBuilt:{}结束流程" + processEngine);
    }


}