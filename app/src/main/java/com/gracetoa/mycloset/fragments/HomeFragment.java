package com.gracetoa.mycloset.fragments;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.HomeAdapter;
import com.gracetoa.mycloset.models.Category;
import com.gracetoa.mycloset.models.SubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<Category> categories;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter homeAdapter;

    private Realm realm;
    private RealmResults<Category> realmResults;

    //constructor default
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_home);
        realm = Realm.getDefaultInstance();
        realmResults = realm.where(Category.class).findAll();

        if (realmResults.size() == 0){
            createCategory();
        }

        homeAdapter = new HomeAdapter(realmResults, R.layout.card_view_item_home, new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category, int position) {
                Toast.makeText(getContext(),category.toString(),Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.smoothScrollToPosition(0);

        return view;
    }

    //*  Realm */
    private void createCategory(){
        List<Category> c = getCategories();
        realm.beginTransaction();
        realm.copyToRealm(c);
        realm.commitTransaction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public  List<Category> getCategories(){

        final RealmList<SubCategory> subCategoriesAccesories = new RealmList<>();
        subCategoriesAccesories.add(new SubCategory("Glasses",R.mipmap.ic_subcateg_glasses));
        subCategoriesAccesories.add(new SubCategory("Bags",R.mipmap.ic_subcateg_bag));
        subCategoriesAccesories.add(new SubCategory("Jewelries",R.mipmap.ic_subcateg_jewelry));
        subCategoriesAccesories.add(new SubCategory("Watchs",R.mipmap.ic_subcateg_watch));

        final RealmList<SubCategory> subCategoriesCoats = new RealmList<>();
        subCategoriesCoats.add(new SubCategory("Blazers",R.mipmap.ic_subcateg_blazer));
        subCategoriesCoats.add(new SubCategory("Jackets",R.mipmap.ic_subcateg_jacket));
        subCategoriesCoats.add(new SubCategory("Parkas",R.mipmap.ic_subcateg_parka));
        subCategoriesCoats.add(new SubCategory("Wrap Coat",R.mipmap.ic_subcateg_coat));

        final RealmList<SubCategory> subCategoriesDresses = new RealmList<>();
        subCategoriesDresses.add(new SubCategory("Skirts",R.mipmap.ic_subcateg_skirt));
        subCategoriesDresses.add(new SubCategory("Shirts Dresses",R.mipmap.ic_subcateg_dress));

        final RealmList<SubCategory> subCategoriesShoes = new RealmList<>();
        subCategoriesShoes.add(new SubCategory("Boots",R.mipmap.ic_subcateg_boot));
        subCategoriesShoes.add(new SubCategory("Sandals",R.mipmap.ic_subcateg_sandals));
        subCategoriesShoes.add(new SubCategory("Sneakers",R.mipmap.ic_subcateg_sneakers));
        subCategoriesShoes.add(new SubCategory("Stilettos",R.mipmap.ic_subcateg_stiletto));

        final RealmList<SubCategory> subCategoriesTops = new RealmList<>();
        subCategoriesTops.add(new SubCategory("Shirts",R.mipmap.ic_subcateg_shirt));
        subCategoriesTops.add(new SubCategory("T-Shirts",R.mipmap.ic_subcateg_tshirt));
        subCategoriesTops.add(new SubCategory("Blouses",R.mipmap.ic_subcateg_blouse));

        final RealmList<SubCategory> subCategoriesBottoms = new RealmList<>();
        subCategoriesBottoms.add(new SubCategory("Jeans",R.mipmap.ic_subcateg_jeans));
        subCategoriesBottoms.add(new SubCategory("Leggings",R.mipmap.ic_subcateg_leggings));
        subCategoriesBottoms.add(new SubCategory("Shorts",R.mipmap.ic_subcateg_short));

        return new ArrayList<Category>(){{
            add(new Category("Accessories", R.drawable.accessories,subCategoriesAccesories));
            add(new Category("Coat", R.drawable.coat,subCategoriesCoats));
            add(new Category("Dresses", R.drawable.dresses,subCategoriesDresses));
            add(new Category("Shoes", R.drawable.shoes,subCategoriesShoes));
            add(new Category("Tops", R.drawable.tops,subCategoriesTops));
            add(new Category("Bottoms", R.drawable.trousers,subCategoriesBottoms));
        }};
    }



}