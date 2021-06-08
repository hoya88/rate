package com.example.huilv;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private String[] title = new String[]{"First","Second","Third"};

    public PageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new FirstFragment();
        }else if(position==1){
            return new SecondFragment();
        }else{
            return new ThirdFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(position == 0){
            return "First";
        }
        return title[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
