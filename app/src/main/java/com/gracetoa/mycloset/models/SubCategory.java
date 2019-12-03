package com.gracetoa.mycloset.models;

import com.gracetoa.mycloset.app.ConfigRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-12-01.
 */
public class SubCategory extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private int icon;

    public SubCategory(){}

    public SubCategory(String name, int icon) {
        this.id = ConfigRealm.SubCategoryID.incrementAndGet();
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }


    public int getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                '}';
    }
}
