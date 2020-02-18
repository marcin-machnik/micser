package com.rogaszy.micser.microservice.loss.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import com.rogaszy.micser.microservice.common.service.JdbcRepositoryWrapper;
import com.rogaszy.micser.microservice.loss.Loss;
import com.rogaszy.micser.microservice.loss.LossService;

import java.util.List;
import java.util.stream.Collectors;

public class LossServiceImpl extends JdbcRepositoryWrapper implements LossService {

    private static final int PAGE_LIMIT = 7;

    public LossServiceImpl(Vertx vertx, JsonObject config) {
        super(vertx, config);
    }

    @Override
    public LossService initializePersistence(Handler<AsyncResult<Void>> resultHandler) {
        client.getConnection(connHandler(resultHandler, connection -> {
            connection.execute(CREATE_STATEMENT, r -> {
                resultHandler.handle(r);
                connection.close();
            });
        }));
        return this;
    }

    @Override
    public LossService addLoss(Loss loss, Handler<AsyncResult<Void>> resultHandler) {
        JsonArray params = new JsonArray()
                .add(loss.getLossId())
                .add(loss.getLossAmount());
        executeNoResult(params, INSERT_STATEMENT, resultHandler);
        return this;
    }

    @Override
    public LossService retrieveLoss(String lossId, Handler<AsyncResult<Loss>> resultHandler) {
        this.retrieveOne(lossId, FETCH_STATEMENT)
                .map(option -> option.map(Loss::new).orElse(null))
                .setHandler(resultHandler);
        return this;
    }

    @Override
    public LossService retrieveLossAmount(String lossId, Handler<AsyncResult<JsonObject>> resultHandler) {
        this.retrieveOne(lossId, "SELECT \"lossAmount\" FROM \"Losses\" WHERE \"lossId\" = ?")
                .map(option -> option.orElse(null))
                .setHandler(resultHandler);
        return this;
    }

    @Override
    public LossService retrieveLossByPage(int page, Handler<AsyncResult<List<Loss>>> resultHandler) {
        this.retrieveByPage(page, PAGE_LIMIT, FETCH_WITH_PAGE_STATEMENT)
                .map(rawList -> rawList.stream()
                        .map(Loss::new)
                        .collect(Collectors.toList())
                )
                .setHandler(resultHandler);
        return this;
    }

    @Override
    public LossService retrieveAllLoss(Handler<AsyncResult<List<Loss>>> resultHandler) {
        this.retrieveAll(FETCH_ALL_STATEMENT)
                .map(rawList -> rawList.stream()
                        .map(Loss::new)
                        .collect(Collectors.toList())
                )
                .setHandler(resultHandler);
        return this;
    }

    @Override
    public LossService deleteLoss(String lossId, Handler<AsyncResult<Void>> resultHandler) {
        this.removeOne(lossId, DELETE_STATEMENT, resultHandler);
        return this;
    }

    @Override
    public LossService deleteAllLoss(Handler<AsyncResult<Void>> resultHandler) {
        this.removeAll(DELETE_ALL_STATEMENT, resultHandler);
        return this;
    }

    // SQL statements

    private static final String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS \"Losses\" " +
            "( \"lossId\" VARCHAR(60) NOT NULL, " +
            "  \"lossAmount\" double NOT NULL,  " +
            " PRIMARY KEY (\"lossId\") )";
    private static final String INSERT_STATEMENT = "INSERT INTO \"Losses\" (\"lossId\", \"lossAmount\") VALUES (?, ?)";
    private static final String FETCH_STATEMENT = "SELECT * FROM \"Losses\" WHERE \"lossId\" = ?";
    private static final String FETCH_ALL_STATEMENT = "SELECT * FROM \"Losses\"";
    private static final String FETCH_WITH_PAGE_STATEMENT = "SELECT * FROM \"Losses\" LIMIT ? OFFSET ?";
    private static final String DELETE_STATEMENT = "DELETE FROM \"Losses\" WHERE \"lossId\" = ?";
    private static final String DELETE_ALL_STATEMENT = "DELETE FROM \"Losses\"";
}