package com.julien.saevecke.learnerjvm;

import com.julien.saevecke.learnerjvm.configurations.RabbitMQ;
import com.julien.saevecke.learnerjvm.mealy.Coffee;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static void main(String[] args) {
        SpringApplication.run(LearnerJvmApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.directory(new File("/Users/test/Documents/Projects/bachelor-thesis-2022/resources/deployment/graalvm"));
        processBuilder.command("/bin/zsh", "deploy.sh");
        try {
            processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        admin.purgeQueue(RabbitMQ.SUL_INPUT_QUEUE);
        admin.purgeQueue(RabbitMQ.SUL_OUTPUT_QUEUE);

        coffeeExperiment.learn();
    }
}
