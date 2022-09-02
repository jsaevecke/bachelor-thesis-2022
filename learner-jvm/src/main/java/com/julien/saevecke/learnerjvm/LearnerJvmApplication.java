package com.julien.saevecke.learnerjvm;

import com.julien.saevecke.learnerjvm.configurations.RabbitMQ;
import com.julien.saevecke.learnerjvm.mealy.Coffee;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class LearnerJvmApplication {

    @Autowired
    private Coffee coffeeExperiment;

    @Autowired
    private RabbitAdmin admin;

    @Value("${SUL_DEPLOY_ENABLED:false}")
    private boolean sulDeployEnabled = false;

    @Value("${DEPLOY_SCRIPT_PATH:/Users/test/Documents/Projects/bachelor-thesis-2022/resources/deployment/graalvm")
    private String deployScriptPath = "/Users/test/Documents/Projects/bachelor-thesis-2022/resources/deployment/graalvm";

    @Value("${DEPLY_SCRIPT_NAME:/Users/test/Documents/Projects/bachelor-thesis-2022/resources/deployment/graalvm")
    private String deployScriptName = "deploy.sh";

    @Value("${BASH_COMMAND:/bin/zsh")
    private String bashCommand = "/bin/zsh";

    @Value("${PURGE_ON_START_ENABLED:true}")
    private boolean purgeOnStartEnabled = true;

    public static void main(String[] args) {
        SpringApplication.run(LearnerJvmApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        if(sulDeployEnabled) {
            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.directory(new File(deployScriptPath));
            processBuilder.command(bashCommand, deployScriptName);
            try {
                processBuilder.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(purgeOnStartEnabled) {
            admin.purgeQueue(RabbitMQ.SUL_INPUT_QUEUE);
            admin.purgeQueue(RabbitMQ.SUL_OUTPUT_QUEUE);
        }

        coffeeExperiment.learn();
    }
}
