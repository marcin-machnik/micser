# micser

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
[INFO] INFO: **** [SERVICE INVOCATION SUCCEED] ****
```