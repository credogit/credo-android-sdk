package com.impact.credopayment.Api.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyCardSchema extends ApiResponse implements Serializable {
    @SerializedName("orderId")
    @Expose
    String orderId;

    @SerializedName("transactionId")
    @Expose
    String transactionId;

    @SerializedName("gatewayCode")
    @Expose
    String gatewayCode;

    @SerializedName("gatewayRecommendation")
    @Expose
    String gatewayRecommendation;

    @SerializedName("correlationId")
    @Expose
    String correlationId;

    @SerializedName("timeOfRecord")
    @Expose
    String timeOfRecord;

    @SerializedName("redirectionHtml")
    @Expose
    String redirectionHtml;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }

    public String getGatewayRecommendation() {
        return gatewayRecommendation;
    }

    public void setGatewayRecommendation(String gatewayRecommendation) {
        this.gatewayRecommendation = gatewayRecommendation;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getTimeOfRecord() {
        return timeOfRecord;
    }

    public void setTimeOfRecord(String timeOfRecord) {
        this.timeOfRecord = timeOfRecord;
    }

    public String getRedirectionHtml() {
        return redirectionHtml;
    }

    public void setRedirectionHtml(String redirectionHtml) {
        this.redirectionHtml = redirectionHtml;
    }
}
