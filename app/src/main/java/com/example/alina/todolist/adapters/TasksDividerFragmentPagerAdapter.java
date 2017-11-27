package com.example.alina.todolist.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alina.todolist.R;
import com.example.alina.todolist.fragments.AllTasksFragment;
import com.example.alina.todolist.fragments.DoneTasksFragment;
import com.example.alina.todolist.fragments.ExpiredTasksFragment;

/**
 * Created by Alina on 27.11.2017.
 */

public class TasksDividerFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public TasksDividerFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllTasksFragment();
            case 1:
                return new ExpiredTasksFragment();
            case 2:
                return new DoneTasksFragment();
            default:
                return new AllTasksFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.all_tasks);
            case 1:
                return context.getString(R.string.expired_task);
            case 2:
                return context.getString(R.string.done_task);
            default:
                return null;
        }
    }
}
