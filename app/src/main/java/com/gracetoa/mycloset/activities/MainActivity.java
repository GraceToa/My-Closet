package com.gracetoa.mycloset.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import io.github.yavski.fabspeeddial.FabSpeedDial;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.tabs.TabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.PagerAdapter;
import com.gracetoa.mycloset.fragments.AddEditClotheFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

  
}
