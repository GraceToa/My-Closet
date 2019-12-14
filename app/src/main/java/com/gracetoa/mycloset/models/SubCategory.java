package com.gracetoa.mycloset.models;


import com.gracetoa.mycloset.app.ConfigRealm;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-12-01.
 */
public class SubCategory extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private int icon;

    private RealmList<Clothe> clothes;

    @LinkingObjects("subCategories")
    private final RealmResults<Category> categoriesParent = null;

    public SubCategory(){}

    public SubCategory(String name, int icon) {
        this.id = ConfigRealm.SubCategoryID.incrementAndGet();
        this.name = name;
        this.icon = icon;
        this.clothes = new RealmList<Clothe>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public RealmList<Clothe> getClothes() {
        return clothes;
    }

    public RealmResults<Category> getCategoriesParent() {
        return categoriesParent;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", clothes=" + clothes +
                '}';
    }
}
