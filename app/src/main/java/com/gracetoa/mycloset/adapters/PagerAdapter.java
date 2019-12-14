package com.gracetoa.mycloset.adapters;


import com.gracetoa.mycloset.fragments.ClothesFragment;
import com.gracetoa.mycloset.fragments.HomeFragment;
import com.gracetoa.mycloset.fragments.OutfitsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-11-16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ClothesFragment();
            case 2:
                return new OutfitsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
