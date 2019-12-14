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
public class Type{

    private int id;
    private String name;


    public Type(){}

    public Type(String name) {
        this.id = 0;
        this.name = name;
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



}
