package com.gracetoa.mycloset.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.HomeAdapter;
import com.gracetoa.mycloset.models.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<Category> categories;
    private RecyclerView recyclerViewHome;
    private RecyclerView.Adapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //constructor default
    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home,container,false);

        categories = new ArrayList<Category>();

        recyclerViewHome = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());

        getCategories();


        homeAdapter = new HomeAdapter(categories, R.layout.recycler_view_item, new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category, int position) {
                //Toast.makeText(getActivity(),category.toString(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.setAdapter(homeAdapter);

        return view;
    }

    public  void getCategories(){
        String[] category = {"Accessories","Coat","Dresses","Shoes","Tops","Trousers"};
        categories.add(new Category(1,category[0], R.drawable.accessories));
        categories.add(new Category(2,category[1], R.drawable.coat));
        categories.add(new Category(3,category[2], R.drawable.dresses));
        categories.add(new Category(4,category[3], R.drawable.shoes));
        categories.add(new Category(5,category[4], R.drawable.tops));
        categories.add(new Category(6,category[5], R.drawable.trousers));

    }

}
