package com.rogaszy.micser.microservice.loss;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Loss data object.
 *
 * @author Eric Zhao
 */
@DataObject(generateConverter = true)
public class Loss {

    private String lossId;
    private double lossAmount = 0.0d;


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

    public Loss setLossId(String lossId) {
        this.lossId = lossId;
        return this;
    }

    public double getLossAmount() {
        return lossAmount;
    }

    public Loss setLossAmount(double lossAmount) {
        this.lossAmount = lossAmount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loss loss = (Loss) o;

        return lossId.equals(loss.lossId);
    }

    @Override
    public int hashCode() {
        int result = lossId.hashCode();
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return this.toJson().encodePrettily();
    }
}
