# Bachelor Thesis 13.10.2022 - Author: Julien Saevecke
This project includes the following:
* modules
  * learner-graalvm (‘Learner‘ to learn the ‘SUL‘s using ‘LearnLib‘ & ‘Spring Native‘ running on ‘GraalVM‘)
    * more performant than the ‘learner-jvm‘, but has not a noticable impact on the total learn time of a SUL in the context of a cluster setup
  * learner-jvm (‘Learner‘ to learn the ‘SUL‘s using ‘LearnLib‘ & ‘Spring Boot‘ running on ‘JVM‘)
  * sul-graalvm (‘System Under Learning‘ using ‘Spring Native‘ running on ‘GraalVM‘)
    * more performant than ‘sul-jvm‘, has a big impact on the total learn time of a SUL in the context of a cluster setup
  * sul-jvm (‘System Under Learning‘ using ‘Spring Boot‘ running on ‘JVM‘)
* resources
  * scripts & configurations for kubernetes/docker
    * ‘setup-cluster.sh‘ to set up a ready to go ‘minikube‘ cluster including
      * rabbitmq
      * prometheus
      * grafana
      * dashboard
    * dockerfiles & docker-compose used on ‘*-jvm‘ modules - goal being:
      * reduced start up time
      * reduced image pull time
      * reduced used up space
    * deployment setup configurations and scripts to deploy them:
      * namespace (sul)
      * configmap (sul environment variables)
      * rabbitmq secret (important to connect to rabbitmq)
      * scaling
      * sul-jvm evaluation setups
      * sul-graalvm evaluation setups
## About
## Why
## ...
### Automata Learning at Scale: Evaluation of Query Parallelisation Strategies in the Context of Clustered Systems

