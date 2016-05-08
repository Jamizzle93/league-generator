package com.mysticwater.leaguegenerator;

import com.mysticwater.leaguegenerator.LeagueContract.LeagueEntry;
import com.mysticwater.leaguegenerator.LeagueContract.TeamEntry;

public class LeagueDbHelper {

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
                    LeagueEntry.COLUMN_NAME_TEAM_TABLE_REF + TEXT_TYPE + COMMA_SEP +
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

}
