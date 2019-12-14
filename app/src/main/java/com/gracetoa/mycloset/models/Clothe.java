package com.gracetoa.mycloset.models;

import android.graphics.Color;

import com.gracetoa.mycloset.app.ConfigRealm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-11-16.
 */
public class Clothe extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private ColorName colorName;
    private String imageClothe;

    @LinkingObjects("clothes")
    private final RealmResults<SubCategory>subCategParent = null;

    public Clothe() {}

    public Clothe(ColorName colorName, String imageClothe) {
        this.id = ConfigRealm.ClotheID.incrementAndGet();
        this.colorName = colorName;
        this.imageClothe = imageClothe;
    }


    public int getId() {
        return id;
    }

    public ColorName getColorName() {
        return colorName;
    }

    public void setColorName(ColorName colorName) {
        this.colorName = colorName;
    }

    public String getImageClothe() {
        return imageClothe;
    }

    public void setImageClothe(String imageClothe) {
        this.imageClothe = imageClothe;
    }

    public RealmResults<SubCategory> getSubCategoryParent() {
        return subCategParent;
    }

    public Clothe(int id, int subCategoryID, ColorName colorName, String imageClothe) {
        this.id = id;
        this.colorName = colorName;
        this.imageClothe = imageClothe;
    }

}
