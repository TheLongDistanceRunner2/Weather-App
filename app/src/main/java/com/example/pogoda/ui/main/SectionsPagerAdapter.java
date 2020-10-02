package com.example.pogoda.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pogoda.CurrentWeatherFragment;
import com.example.pogoda.FiveDaysWeatherFragment;
import com.example.pogoda.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        // zmiana fragmentow:
        switch(position) {
            case 0:
                fragment = new CurrentWeatherFragment();
                        // = new BlankFragment.newInstance( argumenty );
                break;
            case 1:
                fragment = new FiveDaysWeatherFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch(position) {
            case 0:
                title = "Aktualna:";
                break;
            case 1:
                title = "5-cio dniowa:";
                break;
        }

        return title;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}