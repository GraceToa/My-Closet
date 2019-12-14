package com.gracetoa.mycloset.models;


import com.gracetoa.mycloset.app.ConfigRealm;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-11-16.
 */
public class Category extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private int image;

    private RealmList<SubCategory> subCategories;

    public Category(){}

    public Category( String name, int image, RealmList<SubCategory>subCategories) {
        this.id = ConfigRealm.CategoryID.incrementAndGet();
        this.name = name;
        this.image = image;
        this.subCategories = subCategories;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public RealmList<SubCategory> getSubCategories() {
        return subCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", subCategories=" + subCategories +
                '}';
    }
}
