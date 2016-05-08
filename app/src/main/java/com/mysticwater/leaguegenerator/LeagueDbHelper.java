package com.mysticwater.leaguegenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mysticwater.leaguegenerator.LeagueContract.LeagueEntry;
import com.mysticwater.leaguegenerator.LeagueContract.TeamEntry;

import java.util.ArrayList;

public class LeagueDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "League.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_LEAGUE =
            "CREATE TABLE " + LeagueEntry.TABLE_NAME + " (" +
                    LeagueEntry._ID + " INTEGER PRIMARY KEY," +
                    LeagueEntry.COLUMN_NAME_LEAGUE_ID + TEXT_TYPE + COMMA_SEP +
                    LeagueEntry.COLUMN_NAME_LEAGUE_NAME + TEXT_TYPE + COMMA_SEP +
                    LeagueEntry.COLUMN_NAME_TEAM_TABLE_ID + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_TEAM =
            "CREATE TABLE " + TeamEntry.TABLE_NAME + " (" +
                    TeamEntry._ID + " INTEGER PRIMARY KEY," +
                    TeamEntry.COLUMN_NAME_TEAM_ID + TEXT_TYPE + COMMA_SEP +
                    TeamEntry.COLUMN_NAME_TEAM_NAME + TEXT_TYPE + COMMA_SEP +
                    TeamEntry.COLUMN_NAME_TEAM_POINTS + INT_TYPE + COMMA_SEP +
                    TeamEntry.COLUMN_NAME_TEAM_WIN + INT_TYPE + COMMA_SEP +
                    TeamEntry.COLUMN_NAME_TEAM_DRAW + INT_TYPE + COMMA_SEP +
                    TeamEntry.COLUMN_NAME_TEAM_LOSS + INT_TYPE + COMMA_SEP +
                    TeamEntry.COLUMN_NAME_TEAM_SCORE_FOR + INT_TYPE + COMMA_SEP +
                    TeamEntry.COLUMN_NAME_TEAM_SCORE_AGAINST + INT_TYPE + COMMA_SEP +
                    " )";

    /**
     * League Projection Array
     */
    String[] leagueProjection = {
            LeagueEntry._ID,
            LeagueEntry.COLUMN_NAME_LEAGUE_ID,
            LeagueEntry.COLUMN_NAME_LEAGUE_NAME,
            LeagueEntry.COLUMN_NAME_TEAM_TABLE_ID
    };

    public LeagueDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LEAGUE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long createLeague(String leagueName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LeagueEntry.COLUMN_NAME_LEAGUE_ID, "0");
        values.put(LeagueEntry.COLUMN_NAME_LEAGUE_NAME, leagueName);
        values.put(LeagueEntry.COLUMN_NAME_TEAM_TABLE_ID, "0");

        // insert row
        return db.insert(LeagueEntry.TABLE_NAME, null, values);
    }

    public int deleteLeague(long leagueId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = "_ID = ?";

        String[] selectionArgs = new String[]{String.valueOf(leagueId)};

        return db.delete(LeagueEntry.TABLE_NAME, selection, selectionArgs);
    }

    public ArrayList<String> getLeagueList() {
        ArrayList<String> leagueNames = new ArrayList<>();

        Cursor cursor = getAllLeaguesCursor();

        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndex(LeagueEntry.COLUMN_NAME_LEAGUE_NAME)
            );
            leagueNames.add(name);
        }
        cursor.close();

        return leagueNames;
    }

    public ArrayList<Long> getLeagueIdList() {
        ArrayList<Long> leagueIds = new ArrayList<>();

        Cursor cursor = getAllLeaguesCursor();

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(
                    cursor.getColumnIndex(LeagueEntry._ID)
            );
            leagueIds.add(id);
        }
        cursor.close();

        return leagueIds;
    }

    public Cursor getAllLeaguesCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                LeagueEntry.TABLE_NAME,
                leagueProjection,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }
}
