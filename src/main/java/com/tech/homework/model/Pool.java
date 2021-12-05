package com.tech.homework.model;

import java.util.List;

public class Pool {
    private int poolId;
    private List<Integer> poolValues;
    private double percentile;

    public int getPoolId() {
        return poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }

    public List<Integer> getPoolValues() {
        return poolValues;
    }

    public void setPoolValues(List<Integer> poolValues) {
        this.poolValues = poolValues;
    }

    public double getPercentile() {
        return percentile;
    }

    public void setPercentile(double percentile) {
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
