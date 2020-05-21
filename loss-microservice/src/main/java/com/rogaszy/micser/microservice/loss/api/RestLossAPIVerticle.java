package com.rogaszy.micser.microservice.loss.api;

import io.vertx.core.Future;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import com.rogaszy.micser.microservice.common.RestAPIVerticle;
import com.rogaszy.micser.microservice.loss.Loss;
import com.rogaszy.micser.microservice.loss.LossService;


/**
 * This verticle exposes a HTTP endpoint to process shopping loss management with REST APIs.
 *
 * @author Eric Zhao
 */
public class RestLossAPIVerticle extends RestAPIVerticle {

    public static final String SERVICE_NAME = "loss-rest-api";

    private static final String API_ADD = "/add";
//    private static final String API_RETRIEVE_BY_PAGE = "/loss";
    private static final String API_RETRIEVE_ALL = "/loss";
    private static final String API_RETRIEVE_AMOUNT = "/loss/:lossId/amount";
    private static final String API_RETRIEVE = "/loss/:lossId";
    private static final String API_UPDATE = "/:lossId";
    private static final String API_DELETE = "/:lossId";
    private static final String API_DELETE_ALL = "/all";

    private final LossService service;
    
    public RestLossAPIVerticle(LossService service) {
        this.service = service;
    }

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        final Router router = Router.router(vertx);
        // body handler
        router.route().handler(BodyHandler.create());
        // API route handler
        router.post(API_ADD).handler(this::apiAdd);
//        router.get(API_RETRIEVE_BY_PAGE).handler(this::apiRetrieveByPage);
        router.get(API_RETRIEVE_ALL).handler(this::apiRetrieveAll);
        router.get(API_RETRIEVE_AMOUNT).handler(this::apiRetrieveAmount);
        router.get(API_RETRIEVE).handler(this::apiRetrieve);
        router.patch(API_UPDATE).handler(this::apiUpdate);
        router.delete(API_DELETE).handler(this::apiDelete);
        router.delete(API_DELETE_ALL).handler(/*context -> requireLogin(context,*/ this::apiDeleteAll);

        // get HTTP host and port from configuration, or use default value
        String host = config().getString("loss.http.address", "0.0.0.0");
        int port = config().getInteger("loss.http.port", 8082);

        // create HTTP server and publish REST service
        createHttpServer(router, host, port)
                .compose(serverCreated -> publishHttpEndpoint(SERVICE_NAME, host, port))
                .setHandler(future.completer());
    }

    private void apiAdd(RoutingContext context) {
        try {
            Loss loss = new Loss(new JsonObject(context.getBodyAsString()));
            service.addLoss(loss, resultHandler(context, r -> {
                String result = new JsonObject().put("message", "loss_added")
                        .put("lossId", loss.getLossId())
                        .encodePrettily();
                context.response().setStatusCode(201)
                        .putHeader("content-type", "application/json")
                        .end(result);
            }));
        } catch (DecodeException e) {
            badRequest(context, e);
        }
    }

    private void apiRetrieve(RoutingContext context) {
        String lossId = context.request().getParam("lossId");
        service.retrieveLoss(lossId, resultHandlerNonEmpty(context));
    }

    private void apiRetrieveAmount(RoutingContext context) {
        String lossId = context.request().getParam("lossId");
        service.retrieveLossAmount(lossId, resultHandlerNonEmpty(context));
    }

    private void apiRetrieveByPage(RoutingContext context) {
        try {
            String p = context.request().getParam("p");
            int page = p == null ? 1 : Integer.parseInt(p);
            service.retrieveLossByPage(page, resultHandler(context, Json::encodePrettily));
        } catch (Exception ex) {
            badRequest(context, ex);
        }
    }

    private void apiRetrieveAll(RoutingContext context) {
        service.retrieveAllLoss(resultHandler(context, Json::encodePrettily));
    }

    private void apiUpdate(RoutingContext context) {
        notImplemented(context);
    }

    private void apiDelete(RoutingContext context) {
        String lossId = context.request().getParam("lossId");
        service.deleteLoss(lossId, deleteResultHandler(context));
    }

    private void apiDeleteAll(RoutingContext context) {
        service.deleteAllLoss(deleteResultHandler(context));
    }

}
