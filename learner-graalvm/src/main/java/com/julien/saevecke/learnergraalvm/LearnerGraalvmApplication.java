package com.julien.saevecke.learnergraalvm;

import com.julien.saevecke.learnergraalvm.membership.RabbitMQOracle;
import com.julien.saevecke.shared.configurations.RabbitMQ;
import com.julien.saevecke.shared.messages.DefaultQueryProxy;
import com.julien.saevecke.shared.messages.MembershipQuery;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.*;

import java.util.ArrayList;

//@TypeHint(types = {ArrayList.class, MembershipQuery.class}, access = {TypeAccess.DECLARED_CLASSES, TypeAccess.DECLARED_FIELDS, TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS, TypeAccess.PUBLIC_CLASSES, TypeAccess.PUBLIC_CONSTRUCTORS, TypeAccess.PUBLIC_FIELDS, TypeAccess.PUBLIC_METHODS})
//@SerializationHint(types = {MembershipQuery.class,ArrayList.class})
//@InitializationHint(types = RabbitMQ.class, initTime = InitializationTime.RUN)
/*@TypeHint(
        types = RabbitMQ.class,
        methods = @MethodHint(name = "setTemplate",parameterTypes = RabbitMQOracle.class ))*/
@SpringBootApplication
public class LearnerGraalvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnerGraalvmApplication.class, args);
    }

}
