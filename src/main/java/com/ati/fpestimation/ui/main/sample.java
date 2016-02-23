package com.ati.fpestimation.ui.main;

public class sample {
    private int age;
    private String sex;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public sample(int age, String sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }
}