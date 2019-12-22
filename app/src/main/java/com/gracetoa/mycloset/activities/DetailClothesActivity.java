package com.gracetoa.mycloset.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.fragments.ClothesFragment;
import com.gracetoa.mycloset.models.Category;
import com.gracetoa.mycloset.models.Clothe;
import com.gracetoa.mycloset.models.SubCategory;

import java.io.File;

public class DetailClothesActivity extends AppCompatActivity {


    private Clothe clothes;
    private Realm realm;
    private RealmResults<Clothe> clotheRealmResults;
    RealmResults<Category>categoriesParent;
    RealmResults<SubCategory>subCategParent;
    private int clotesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_clothes);

        realm = Realm.getDefaultInstance();

        ImageView imageClothes = findViewById(R.id.image_clothes_detail);
        TextView nameSubcateg = findViewById(R.id.name_subcateg_detail_clothes);
        TextView nameCateg = findViewById(R.id.name_categ_detail_clothes);
        TextView colorName = findViewById(R.id.color_name_detail_clothes);
        View colorClothe = findViewById(R.id.color_circle);
        FloatingActionButton fab = findViewById(R.id.fab_detail_cothes);

        if (getIntent().getExtras() != null) {
            clotesID = getIntent().getExtras().getInt("id");

            clothes = realm.where(Clothe.class).equalTo("id", clotesID).findFirst();


            subCategParent = clothes.getSubCategoryParent();
            for (SubCategory sb: subCategParent
            ) {
                nameSubcateg.setText(sb.getName());
                sb.getCategoriesParent();
                for (Category cat: sb.getCategoriesParent()
                ) {
                    nameCateg.setText(cat.getName());
                }
            }

            File imgFile = new File(clothes.getImageClothe());
            if(imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageClothes.setImageBitmap(bitmap);
            }

            colorName.setText(clothes.getColorName().getName());
            colorClothe.setBackgroundColor(Color.rgb(clothes.getColorName().getR(),clothes.getColorName().getG(),clothes.getColorName().getB()));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Delete Clothes ", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar s = Snackbar.make(v,"Successfully delete!", Snackbar.LENGTH_LONG);
                                s.show();
                                realm.beginTransaction();
                                clothes.deleteFromRealm();
                                realm.commitTransaction();
                                finish();
                            }
                        }).show();
            }
        });

    }


}
