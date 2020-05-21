package com.rogaszy.micser.microservice.monitor;

import com.rogaszy.micser.microservice.common.BaseMicroserviceVerticle;
import com.rogaszy.micser.microservice.loss.LossService;
import com.rogaszy.micser.microservice.monitor.api.RestMonitorVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

public class MonitorVerticle extends BaseMicroserviceVerticle{

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);
		
		LossService service = LossService.createProxy(vertx, LossService.SERVICE_ADDRESS);
		
		deployRestService(service);
	}
	
	private Future<Void> deployRestService(LossService service) {
		Future<String> future = Future.future();
		vertx.deployVerticle(new RestMonitorVerticle(service)
				, new DeploymentOptions().setConfig(config()),
				future.completer());
		return future.map(r -> null);
	}
}