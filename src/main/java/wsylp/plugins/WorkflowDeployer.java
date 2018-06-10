package wsylp.plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * @Description: 1.结合Spring自动部署Activit流程定义文件 2.该类需要注册为Spring的组件，启动服务时会自动执行
 *               3.部署前判断流程图是否修改，修改则部署
 *
 */
//@Component
public class WorkflowDeployer {

    private static final Logger logger = Logger.getLogger(WorkflowDeployer.class);
    private Resource[] deploymentResources;

    public Resource[] getDeploymentResources() {
        return deploymentResources;
    }

    public void setDeploymentResources(Resource[] deploymentResources) {
        this.deploymentResources = deploymentResources;
    }

    /**
     * 利用构造方法初始化变量
     * 
     * @throws Exception
     */
    private WorkflowDeployer() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        deploymentResources = resolver.getResources("classpath:/study/activiti/diagrams/autoDeploy/*.bpmn");
        deploying();
    }

    /**
     * 部署流程资源文件数据
     * 
     * @throws Exception
     */
    public void deploying() throws Exception {
        logger.info(">>>deploying() - 流程资源部署开始");
        if (deploymentResources != null) {
            /* 创建流程引擎配置对象 */
            ProcessEngineConfiguration config = ProcessEngineConfiguration
                    .createProcessEngineConfigurationFromResource("gms/spring-activiti.cfg.xml");
            /* 根据配置对象创建工作流引擎 */
            ProcessEngine engine = config.buildProcessEngine();
            /* 得到仓库服务对象 */
            RepositoryService repositoryService = engine.getRepositoryService();
            for (Resource r : deploymentResources) {
                String deploymentName = r.getFilename();
                boolean doDeploy = true;
                List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentName(deploymentName)
                        .orderByDeploymenTime().desc().list();
                if (!deployments.isEmpty()) {
                    Deployment existing = deployments.get(0);
                    try {
                        InputStream in = repositoryService.getResourceAsStream(existing.getId(), deploymentName);
                        if (in != null) {
                            File f = File.createTempFile("deployment", "bpmn",
                                    new File(System.getProperty("java.io.tmpdir")));
                            f.deleteOnExit();
                            OutputStream out = new FileOutputStream(f);
                            IOUtils.copy(in, out);
                            in.close();
                            out.close();
                            doDeploy = (FileUtils.checksumCRC32(f) != FileUtils.checksumCRC32(r.getFile()));
                        } else {
                            throw new ActivitiException("不能读取资源 " + deploymentName + ", 输入流为空");
                        }
                    } catch (ActivitiException ex) {
                        logger.error("Unable to read " + deploymentName + " of deployment " + existing.getName()
                                + ", id: " + existing.getId() + ", will re-deploy");
                    }
                }
                if (doDeploy) {
                    DeploymentBuilder deployment = repositoryService.createDeployment();
                    FileInputStream pngStream = new FileInputStream(r.getFile().toString().replace(".bpmn", ".png"));
                    deployment.name(deploymentName).addInputStream(deploymentName, r.getInputStream());
                    deployment.name(deploymentName).addInputStream(deploymentName.replace(".bpmn", ".png"), pngStream);
                    deployment.deploy();
                }
            }
        }
        logger.info("<<<deploying() - 流程资源部署结束");
    }

}
