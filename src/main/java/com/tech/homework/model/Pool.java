package com.tech.homework.model;

import javax.validation.constraints.NotNull;

import java.util.List;

public class Pool {
    @NotNull
    private Integer poolId;

    private List<Integer> poolValues;
    private Double percentile;

    public Integer getPoolId() {
        return poolId;
    }

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }

    public List<Integer> getPoolValues() {
        return poolValues;
    }

    public void setPoolValues(List<Integer> poolValues) {
        this.poolValues = poolValues;
    }

    public Double getPercentile() {
        return percentile;
    }

    public void setPercentile(Double percentile) {
        this.percentile = percentile;
    }

    @Override
    public String toString() {
        return "Pool{" +
                "poolId=" + poolId +
                ", poolValues=" + poolValues +
                ", percentile=" + percentile +
                '}';
    }
}
