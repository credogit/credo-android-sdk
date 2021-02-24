package com.impact.credopayment.Api.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitiateSchema extends ApiResponse implements Serializable {
    @SerializedName("paymentLink")
    @Expose
    String paymentLink;
    
    @SerializedName("paymentSlug")
    @Expose
    String paymentSlug;

    public String getPaymentSlug() {
        return paymentSlug;
    }

    public void setPaymentSlug(String paymentSlug) {
        this.paymentSlug = paymentSlug;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }


}
