package com.rogaszy.micser.microservice.loss;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Loss {

    private String lossId;
    private double lossAmount;

    public Loss() {
        // Empty constructor
    }

    public Loss(Loss other) {
        this.lossId = other.lossId;
        this.lossAmount = other.lossAmount;
    }

    public Loss(JsonObject json) {
        LossConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LossConverter.toJson(this, json);
        return json;
    }

    public String getLossId() {
        return lossId;
    }

    public double getLossAmount() {
        return lossAmount;
    }

}