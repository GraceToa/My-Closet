package com.gracetoa.mycloset.models;

import com.gracetoa.mycloset.app.ConfigRealm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-12-08.
 */
public class ColorName extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private int r, g, b;
    private String name;

    public ColorName(){}

    public ColorName(String name, int r, int g, int b) {
        this.id = ConfigRealm.ColorNameID.incrementAndGet();
        this.r = r;
        this.g = g;
        this.b = b;
        this.name = name;
    }

    public int computeMSE(int pixR, int pixG, int pixB) {
        return (int) (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b)
                * (pixB - b)) / 3);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public String getName() {
        return name;
    }

}
