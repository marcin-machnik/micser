package com.rogaszy.micser.microservice.loss;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

import com.rogaszy.micser.microservice.loss.impl.LossServiceImpl;


@VertxGen
@ProxyGen
public interface LossService {

    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "loss-eb-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "loss.loss";

    /**
     * A static method that creates a loss service.
     *
     * @param config a json object for configuration
     * @return initialized loss service
     */
    static LossService createService(Vertx vertx, JsonObject config) {
	    return new LossServiceImpl(vertx, config);
	}

	static LossService createProxy(Vertx vertx, String address) {
		return new LossServiceVertxEBProxy(vertx, address);
	}
    
    /**
     * Initialize the persistence.
     *
     * @param resultHandler the result handler will be called as soon as the initialization has been accomplished. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService initializePersistence(Handler<AsyncResult<Void>> resultHandler);

    /**
     * Add a loss to the persistence.
     *
     * @param loss       a loss entity that we want to add
     * @param resultHandler the result handler will be called as soon as the loss has been added. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService addLoss(Loss loss, Handler<AsyncResult<Void>> resultHandler);

    /**
     * Retrieve the loss with certain `lossId`.
     *
     * @param lossId     loss id
     * @param resultHandler the result handler will be called as soon as the loss has been retrieved. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService retrieveLoss(String lossId, Handler<AsyncResult<Loss>> resultHandler);

    /**
     * Retrieve the loss amount with certain `lossId`.
     *
     * @param lossId     loss id
     * @param resultHandler the result handler will be called as soon as the loss has been retrieved. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService retrieveLossAmount(String lossId, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * Retrieve all loss.
     *
     * @param resultHandler the result handler will be called as soon as the loss have been retrieved. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService retrieveAllLoss(Handler<AsyncResult<List<Loss>>> resultHandler);

    /**
     * Retrieve loss by page.
     *
     * @param resultHandler the result handler will be called as soon as the loss have been retrieved. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService retrieveLossByPage(int page, Handler<AsyncResult<List<Loss>>> resultHandler);

    /**
     * Delete a loss from the persistence
     *
     * @param lossId     loss id
     * @param resultHandler the result handler will be called as soon as the loss has been removed. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService deleteLoss(String lossId, Handler<AsyncResult<Void>> resultHandler);

    /**
     * Delete all loss from the persistence
     *
     * @param resultHandler the result handler will be called as soon as the loss have been removed. The async result indicates
     *                      whether the operation was successful or not.
     */
    @Fluent
    LossService deleteAllLoss(Handler<AsyncResult<Void>> resultHandler);

}