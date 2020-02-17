package com.rogaszy.micser.microservice.loss;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import com.rogaszy.micser.microservice.common.BaseMicroserviceVerticle;
import com.rogaszy.micser.microservice.common.service.ExampleHelper;
import com.rogaszy.micser.microservice.loss.api.RestLossAPIVerticle;
import com.rogaszy.micser.microservice.loss.impl.LossServiceImpl;
import io.vertx.serviceproxy.ProxyHelper;

import static com.rogaszy.micser.microservice.loss.LossService.SERVICE_ADDRESS;


/**
 * A verticle publishing the loss service.
 *
 * @author Eric Zhao
 */
public class LossVerticle extends BaseMicroserviceVerticle {

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();

        // create the service instance
        LossService LossService = new LossServiceImpl(vertx, config());
        // register the service proxy on event bus
        ProxyHelper.registerService(LossService.class, vertx, LossService, SERVICE_ADDRESS);
        // publish the service in the discovery infrastructure
        initLossDatabase(LossService)
                .compose(databaseOkay -> publishEventBusService(LossService.SERVICE_NAME, SERVICE_ADDRESS, LossService.class))
                .compose(servicePublished -> deployRestService(LossService))
                .setHandler(future.completer());
    }

    private Future<Void> initLossDatabase(LossService service) {
        Future<Void> initFuture = Future.future();
        service.initializePersistence(initFuture.completer());
        return initFuture.map(v -> {
            ExampleHelper.initData(vertx, config());
            return null;
        });
    }

    private Future<Void> deployRestService(LossService service) {
        Future<String> future = Future.future();
        vertx.deployVerticle(new RestLossAPIVerticle(service),
                new DeploymentOptions().setConfig(config()),
                future.completer());
        return future.map(r -> null);
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new LossVerticle());
    }


}
