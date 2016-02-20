package com.ati.fpestimation.domain.kpi;

public class AppStackType {

    private String shortName;
    private String name;
    private int id;

    public AppStackType() {
    }

    public AppStackType(int id, String name, String shortName) {
        this.id = id;
        this.shortName = shortName;
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AppStackType{" +
                "shortName='" + shortName + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
