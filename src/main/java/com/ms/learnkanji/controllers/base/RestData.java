package com.ms.learnkanji.controllers.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NotBlank
public class RestData<T> {
    private RestStatus restStatus;

    private String message;

    private List<String> reasons;

    private T data;

    public RestData(String message, T data) {
        this.restStatus = RestStatus.SUCCESS;
        this.message = message;
        this.data = data;
    }

    public RestData(String message, List<String> reasons) {
        this.restStatus = RestStatus.ERROR;
        this.message = message;
        this.reasons = reasons;
    }

    public RestData(RestStatus restStatus, String message, List<String> reasons, T data) {
        this.restStatus = restStatus;
        this.message = message;
        this.reasons = reasons;
        this.data = data;
    }

    public RestStatus getRestStatus() {
        return restStatus;
    }

    public void setRestStatus(RestStatus restStatus) {
        this.restStatus = restStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
