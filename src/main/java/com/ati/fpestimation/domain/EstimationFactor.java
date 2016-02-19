package com.ati.fpestimation.domain;

public class EstimationFactor {
    private double factor;
    private int id;
    private String name;

    public EstimationFactor() {
    }

    public EstimationFactor(int id, String name, double factor) {
        this.factor = factor;
        this.id = id;
        this.name = name;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EstimationFactor{" +
                "factor=" + factor +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
