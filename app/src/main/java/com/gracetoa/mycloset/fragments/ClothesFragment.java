package com.gracetoa.mycloset.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.ClothesAdapter;
import com.gracetoa.mycloset.models.Clothe;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClothesFragment extends Fragment {

    private RecyclerView.Adapter clothesAdapter;
    private Realm realm;
    private RealmResults<Clothe> clotheRealmResults;
    private DataListener callback;

    public ClothesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        realm = Realm.getDefaultInstance();
        clotheRealmResults = realm.where(Clothe.class).findAllAsync();

        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                clothesAdapter.notifyDataSetChanged();
            }
        };
        clotheRealmResults.addChangeListener(changeListener);

        clothesAdapter = new ClothesAdapter(clotheRealmResults, R.layout.card_view_item_clothes, new ClothesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Clothe clothe, int position) {

                callback.sendData(clothe);
            }
        });

        recyclerView.setAdapter(clothesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.smoothScrollToPosition(0);

        return view;
    }

    public interface DataListener {
        void sendData(Clothe clothe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (DataListener) context;
        }catch (ClassCastException e){
            throw  new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    /* Events */

}