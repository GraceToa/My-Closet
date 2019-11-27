package com.gracetoa.mycloset.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gracetoa.mycloset.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditClotheFragment extends Fragment {


    public AddEditClotheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_clothe, container, false);
    }

}
