# micser

## Running services 

To run example follow steps:

1. go to microservice-common directory and call  
	**mvn install**  
	
2. go to loss-microservice directory and call  
	**mvn install**  
	
3. from loss-microservice directory call  
	**mvn compile vertx:run**
	
4. from microservice-monitor directory call  
	**mvn compile vertx:run**
	

when all services starts working at the end of microservice-monitor you should see following log snippet:

```
INFO: **** [SERVICE INVOCATION SUCCEED] **** You have connected by Event bus with -> LossService
```

## Debugging services

To debug microservice you need to run command  
** mvn compile vertx:debug -Ddebug.port=8888 -Ddebug.suspend=true **  

with following parameters  

* **debug.port** - port on which microservice will be listening
* **debug.suspend** - true|fasle - if microservice should wait until you will connect to it or it should start without waiting

Next using IDE connect to microservice. For instance, using Eclipse create **Run Configutation** as **Remote Java Application** with port set.