package com.example.itx_m.fifaworldcup2018;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Zohaib on 31/01/2016.
 */
public class CtCustomFragmentPagerAdapter extends FragmentPagerAdapter{

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    int PAGE_COUNT;
    private Bundle extras;
    private HashMap<String, List<CtTeam>> sortedTeams;
    private ArrayList<String> poolsList;

    /** Constructor of the class */
    public CtCustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
        CtFragmentPointsTable fragment = new CtFragmentPointsTable();
        Bundle data = new Bundle();
        data.putSerializable("TeamsList", (java.io.Serializable) sortedTeams.get(poolsList.get(arg0)));
        fragment.setArguments(data);
        registeredFragments.put(arg0, fragment);
        return fragment;
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return poolsList.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public void setBundle(Bundle extras) {
        this.extras = extras;
        this.sortedTeams = (HashMap<String, List<CtTeam>>) extras.getSerializable("SortedTeams");
        this.poolsList = extras.getStringArrayList("Pools");
        this.PAGE_COUNT = poolsList.size();
    }
}
