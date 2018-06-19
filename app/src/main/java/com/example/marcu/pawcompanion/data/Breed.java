package com.example.marcu.pawcompanion.data;

import java.io.Serializable;

/**
 * Created by marcu on 3/18/2018.
 */

public final class Breed implements Serializable {

    private int id;
    private String name;
    private int activityLevel;
    private int sizeLevel;

    public Breed(int id, String name, int activityLevel, int sizeLevel) {
        this.id = id;
        this.name = name;
        this.activityLevel = activityLevel;
        this.sizeLevel = sizeLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getSizeLevel() {
        return sizeLevel;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activityLevel=" + activityLevel +
                ", sizeLevel=" + sizeLevel +
                '}';
    }
}
