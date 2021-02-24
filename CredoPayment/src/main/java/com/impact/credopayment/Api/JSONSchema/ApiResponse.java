package com.impact.credopayment.Api.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("error")
    @Expose
    String error;

    @SerializedName("exception")
    @Expose
    String Exception;

    @SerializedName("path")
    @Expose
    String path;

    @SerializedName("timestamp")
    @Expose
    String timestamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
