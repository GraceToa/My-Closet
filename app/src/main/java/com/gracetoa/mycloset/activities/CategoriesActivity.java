package com.gracetoa.mycloset.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.CategoryAdapter;
import com.gracetoa.mycloset.adapters.ClothesAdapter;
import com.gracetoa.mycloset.models.Category;
import com.gracetoa.mycloset.models.Clothe;
import com.gracetoa.mycloset.models.SubCategory;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private int categID;
    private Realm realm;
    private RecyclerView.Adapter adapter;
    List<Clothe> clothes;
    private RealmResults<Clothe>clotheRealmResults;
    private RealmResults<Category>categoryRealmResults;
    private RealmResults<SubCategory>subCategoryRealmResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        Toolbar toolbar = findViewById(R.id.toolbar_activities);
        realm = Realm.getDefaultInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        TextView customTitleToolbar = toolbar.findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Categories");
        customTitleToolbar.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if (getIntent().getExtras() != null){
           categID =  getIntent().getExtras().getInt("id");


           Category c = realm.where(Category.class).equalTo("id", categID).findFirst();



           List<SubCategory>subCategory = c.getSubCategories();

//
//            for (SubCategory sb: subCategory
//                 ) {
//                List<Clothe> clothesList = sb.getClothes();
//                for (Clothe cl: clothesList
//                     ) {
//                    clothes.add(cl);
//                    Log.i("CLOTHE", cl.toString());
//                }
//            }

            adapter = new CategoryAdapter(subCategory, R.layout.card_view_item_categories, new CategoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(SubCategory subCategory, int position) {

                }
            });
//
//            clothesAdapter = new ClothesAdapter(clothes, R.layout.card_view_item_clothes, new ClothesAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(Clothe clothe, int position) {
//
//                }
//            });
//
//            recyclerView.setAdapter(clothesAdapter);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.smoothScrollToPosition(0);


        }



    }
}
