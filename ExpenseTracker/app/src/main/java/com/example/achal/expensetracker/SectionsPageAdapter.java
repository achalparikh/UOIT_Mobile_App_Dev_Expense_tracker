package com.example.achal.expensetracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by achal on 2017-12-12.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter{

    private final ArrayList<Fragment> mFragList = new ArrayList<>();
    private final ArrayList<String> mFragTitleList = new ArrayList<>();

    public void addFragment (Fragment frag, String title) {
        mFragList.add(frag);
        mFragTitleList.add(title);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragTitleList.get(position);
    }

    //Get position of the TAB for individual categories
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return mFragList.get(position);
    }

    //Get size of the tabs array
    @Override
    public int getCount() {
        return mFragList.size();
    }
}
