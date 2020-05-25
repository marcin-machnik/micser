# micser

microservice-common -> call 
mvn clean install

loss-microservice
eclipse:
  Create Java Application Run. 
  Setup 
    main class as io.vertx.core.Launcher and in 
  Program arguments paste 
    run com.rogaszy.micser.microservice.loss.LossVerticle -conf src\config\local.json



Create maven build run

    compile vertx:run

make sure in pom following properties are set:    
    <vertx.verticle>
    <vertx.config>
    
this configuration will monitor sources and reload if it find changes