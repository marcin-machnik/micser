package com.rogaszy.micser.microservice.monitor.api;

import com.rogaszy.micser.microservice.common.RestAPIVerticle;
import com.rogaszy.micser.microservice.loss.Loss;
import com.rogaszy.micser.microservice.loss.LossService;

import io.vertx.core.Future;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RestMonitorVerticle extends RestAPIVerticle{

    public static final String SERVICE_NAME = "loss-monitor-rest-api";

    private static final String API_ADD = "/monitor/add";
    private static final String API_RETRIEVE_ALL = "/monitor/loss";
    private static final String API_RETRIEVE_ONE = "/monitor/loss/:lossId";
	
    private final LossService service;
    
    public RestMonitorVerticle(LossService service) {
    	this.service = service;
    }
    
	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);
		
		final Router router = Router.router(vertx);
		// body handler
		router.route().handler(BodyHandler.create());
		// API route handler
		router.post(API_ADD).handler(this::apiAdd);
		router.get(API_RETRIEVE_ALL).handler(this::apiRetrieveAll);
		
		  // get HTTP host and port from configuration, or use default value
        String host = config().getString("monitor.http.address", "0.0.0.0");
        int port = config().getInteger("monitor.http.port", 8085);

        // create HTTP server and publish REST service
        createHttpServer(router, host, port)
            .compose(serverCreated -> publishHttpEndpoint(SERVICE_NAME, host, port))
            .setHandler(startFuture.completer());
	}
	

	private void apiAdd(RoutingContext context) {
		try {
			Loss loss = new Loss(new JsonObject(context.getBodyAsString()));
			service.addLoss(loss, resultHandler(context, r -> {
				String result = new JsonObject().put("message", "loss_added").put("lossId", loss.getLossId())
						.encodePrettily();
				context.response().setStatusCode(201).putHeader("content-type", "application/json").end(result);
			}));
		} catch (DecodeException e) {
			badRequest(context, e);
		}
	}

	private void apiRetrieveAll(RoutingContext context) {
		service.retrieveAllLoss(resultHandler(context, Json::encodePrettily));
	}
}
