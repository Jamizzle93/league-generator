package com.mysticwater.leaguegenerator;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class LeagueActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.league_activity);

       mToolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(mToolbar);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       mViewPager = (ViewPager) findViewById(R.id.league_viewpager);
       setupViewPager(mViewPager);

       mTabLayout = (TabLayout) findViewById(R.id.league_tabs);
       mTabLayout.setupWithViewPager(mViewPager);
   }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TeamsFragment(), "TEAMS");
        adapter.addFragment(new TableFragment(), "TABLE");
        adapter.addFragment(new FixturesFragment(), "FIXTURES");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
