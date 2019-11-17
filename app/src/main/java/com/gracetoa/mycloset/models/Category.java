package com.gracetoa.mycloset.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-11-16.
 */
public class Category extends RealmObject {

    private int id;
    private String name;
    private int image;

    public Category(){}

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(int id, String name,int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

}
