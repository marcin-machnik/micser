package com.rogaszy.micser.microservice.loss;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * Loss data object.
 *
 * @author Eric Zhao
 */
@DataObject(generateConverter = true)
public class Loss {

    private String      lossId;
    private Double      lossAmount;
    private String      currency;
    private String      businessLine;
    private Short       linkGroupId;
    //private Date        reportingDate;

    public Loss() {
        // Empty constructor
    }

    public Loss(Loss other) {
        this.lossId = other.lossId;
        this.lossAmount = other.lossAmount;
        this.currency = other.currency;
        this.businessLine = other.businessLine;
        this.linkGroupId = other.linkGroupId;
        //this.reportingDate = other.reportingDate;
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

    public String getCurrency() {
        return currency;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public Short getLinkGroupId() {
        return linkGroupId;
    }

//    public Date getReportingDate() {
//        return reportingDate;
//    }

    public Loss setLossAmount(Double lossAmount) {
        this.lossAmount = lossAmount;
        return this;
    }

    public Loss setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Loss setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
        return this;
    }

    public Loss setLinkGroupId(Short linkGroupId) {
        this.linkGroupId = linkGroupId;
        return this;
    }

//    public Loss setReportingDate(Date reportingDate) {
//        this.reportingDate = reportingDate;
//        return this;
//    }

    public Loss setLossId(String lossId) {
        this.lossId = lossId;
        return this;
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

        return  lossId.equals(loss.lossId) &&
                lossAmount.equals(loss.lossId) &&
                currency.equals(loss.lossId) &&
                businessLine.equals(loss.lossId) &&
                linkGroupId.equals(loss.lossId) ;
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
