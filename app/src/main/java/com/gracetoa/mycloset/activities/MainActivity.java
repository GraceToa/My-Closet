package com.gracetoa.mycloset.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.realm.Realm;

import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.tabs.TabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.PagerAdapter;
import com.gracetoa.mycloset.fragments.ClothesFragment;
import com.gracetoa.mycloset.models.Clothe;


public class MainActivity extends AppCompatActivity implements ClothesFragment.DataListener {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        TextView customTitleToolbar = toolbar.findViewById(R.id.textTitleToolbar);
        setSupportActionBar(toolbar);
        customTitleToolbar.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        getTabLayout(tabLayout);

        FabSpeedDial fabSpeedDial = findViewById(R.id.fabSpeedDial);
        getFabSpeedDial(fabSpeedDial);


    }

    public void getTabLayout(TabLayout tabLayout){
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void getFabSpeedDial(FabSpeedDial fabSpeedDial){
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Add Outfit")){
                    startActivity(new Intent(MainActivity.this,AddEditOutfitActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this,AddEditClotheActivity.class));
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void sendData(Clothe clothe) {

        Intent i = new Intent(this,DetailClothesActivity.class);
        i.putExtra("clotheID", String.valueOf(clothe.getId()));
        startActivity(i);


    }


}

