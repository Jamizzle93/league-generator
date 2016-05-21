package com.mysticwater.leaguegenerator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FixturesFragment extends Fragment {

    // Log tag
    public static final String LOG_TAG = "FixturesFragment";

    // UI Widgets
    private View mLayoutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutView = inflater.inflate(R.layout.team_list_fragment, container, false);

        return mLayoutView;
    }
}
