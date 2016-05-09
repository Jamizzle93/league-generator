package com.mysticwater.leaguegenerator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
    private ArrayList<Long> mLeagueIdArrayList;
    private ArrayList<Long> mLeagueIdsToDelete;
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

        mLeagueIdsToDelete = new ArrayList<>();

        mLeagueList = (ListView) findViewById(R.id.leagues_list);
        mLeagueArrayList = mDb.getLeagueList();
        mLeagueIdArrayList = mDb.getLeagueIdList();
        mLeagueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLeagueArrayList);
        mLeagueList.setAdapter(mLeagueAdapter);
        mLeagueList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        mLeagueList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    long leagueId = mLeagueIdArrayList.get(position);
                    mLeagueIdsToDelete.add(leagueId);
                }
                if (!checked) {
                    long leagueId = mLeagueIdArrayList.get(position);
                    if (mLeagueIdsToDelete.contains(leagueId)) {
                        mLeagueIdsToDelete.remove(leagueId);
                    }
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.delete_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete: {
                        for (long id : mLeagueIdsToDelete) {
                            deleteLeague(id);
                        }
                        return true;
                    }
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        mLeagueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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

    private void deleteLeague(long id) {
        long res = mDb.deleteLeague(id);
        if (res >= 0) {
            Crashlytics.log(Log.INFO, LOG_TAG, "League deleted.");
            updateLeagueList();
        } else {
            Crashlytics.log(Log.ERROR, LOG_TAG, "Failed to delete league.");
        }
    }

    private void updateLeagueList() {
        mLeagueIdArrayList.clear();
        ArrayList<Long> leagueIds = mDb.getLeagueIdList();
        mLeagueIdArrayList.addAll(leagueIds);

        mLeagueArrayList.clear();
        ArrayList<String> leagueList = mDb.getLeagueList();
        mLeagueArrayList.addAll(leagueList);

        mLeagueAdapter.notifyDataSetChanged();
    }
}
