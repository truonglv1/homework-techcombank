package com.tech.homework.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantile() {
        return quantile;
    }

    public void setQuantile(Integer quantile) {
        this.quantile = quantile;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
