package com.mysticwater.leaguegenerator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class LeagueListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LeagueListActivity";

    // UI Widgets
    private ListView mLeagueList;
    private FloatingActionButton mAddLeagueButton;

    private LeagueDbHelper mDb;
    private ArrayList<String> mLeagueArrayList;
    private ArrayAdapter<String> mLeagueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.league_list_activity);

        mDb = new LeagueDbHelper(getApplicationContext());

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAddLeagueButton = (FloatingActionButton) findViewById(R.id.add_league_fab);
        mAddLeagueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddLeagueDialog();
            }
        });

        mLeagueList = (ListView) findViewById(R.id.leagues_list);
        mLeagueArrayList = mDb.getLeagueList();
        mLeagueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLeagueArrayList);
        mLeagueList.setAdapter(mLeagueAdapter);
    }

    private void showAddLeagueDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View view = inflater.inflate(R.layout.add_league_dialog, null);


        builder.setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText leagueNameEdit = (EditText) view.findViewById(R.id.add_league_name);
                        String leagueName = leagueNameEdit.getText().toString();

                        addLeague(leagueName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void addLeague(String leagueName) {
        long res = mDb.createLeague(leagueName);
        if (res >= 0) {
            Crashlytics.log(Log.INFO, LOG_TAG, "League added.");
            updateLeagueList();
        } else {
            Crashlytics.log(Log.ERROR, LOG_TAG, "Failed to add league.");
        }
    }

    private void updateLeagueList() {
        mLeagueArrayList.clear();
        ArrayList<String> leagueList = mDb.getLeagueList();
        mLeagueArrayList.addAll(leagueList);
        mLeagueAdapter.notifyDataSetChanged();
    }
}
